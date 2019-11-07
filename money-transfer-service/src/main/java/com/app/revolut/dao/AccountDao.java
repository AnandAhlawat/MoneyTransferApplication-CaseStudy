package com.app.revolut.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.app.revolut.model.Account;
import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.MoneyTransferReq;
import com.app.revolut.model.Transaction;
import com.app.revolut.model.TransferLimit;
import com.app.revolut.util.Constants;
import com.app.revolut.util.Datastore;
import com.app.revolut.util.ErrorCodeList;

public class AccountDao implements IAccountDao {


	@Override
	public Account getAccountDetails(int authenticatedAccountId){

		return Datastore.getInstance().getAccounts().stream()
				.filter(account -> authenticatedAccountId == account.getAccountNumber())
				.findAny()
				.orElse(null);
	}

	@Override
	public Transaction transferMoney(MoneyTransferReq moneyTransferReq,Beneficiary beneficiary) throws Exception {

		Account sourceAccount = getAccountDetails(moneyTransferReq.getAuthenticatedAccountNumber());
		Account destinationAccount = getAccountDetails(moneyTransferReq.getBeneficiaryAccountNumber());
		Transaction transaction = new Transaction();
		if(sourceAccount==null)
			transaction.addError(ErrorCodeList.INVALID_ACCOUNT);		
		else
		{
			if(destinationAccount==null)
				transaction.addError(ErrorCodeList.INVALID_BENEFICIARY_ACCOUNT);
			else if(beneficiary==null)
				transaction.addError(ErrorCodeList.BENEFECIARY_NOT_LINKED);
			else if(!beneficiary.isVerified())
				transaction.addError(ErrorCodeList.BENEFECIARY_NOT_VERIFIED);
			else
			{
				if((sourceAccount.getAmount().subtract(moneyTransferReq.getAmount())).compareTo(BigDecimal.ZERO) < 0)
				{
					transaction.addError(ErrorCodeList.INSUFFICIENT_FUNDS);
				}
				else
				{
					TransferLimit currentDone = fetchTotalTransactions(sourceAccount.getAccountNumber());
					if(currentDone.getMaxNoOfTransactions()  >=  beneficiary.getTransferLimit().getMaxNoOfTransactions())
						transaction.addError(ErrorCodeList.MAX_TRANS_PER_DAY_EXCEED);
					
					if((currentDone.getDailyLimit().add(moneyTransferReq.getAmount())).compareTo(beneficiary.getTransferLimit().getDailyLimit()) > 0)
						transaction.addError(ErrorCodeList.MAX_AMOUNT_PER_DAY_EXCEED);
					
					if(transaction.hasErrors())
						return transaction;
					
					
					//Actual Transaction
					sourceAccount.setAmount(sourceAccount.getAmount().subtract(moneyTransferReq.getAmount()));
					destinationAccount.setAmount(destinationAccount.getAmount().add(moneyTransferReq.getAmount()));

					//Resyncing the Datastore
					Datastore.getInstance().getAccounts().remove(new Account(sourceAccount.getAccountNumber()));
					Datastore.getInstance().getAccounts().add(sourceAccount);

					Datastore.getInstance().getAccounts().remove(new Account(destinationAccount.getAccountNumber()));
					Datastore.getInstance().getAccounts().add(destinationAccount);

					//Adding transactions in Transactions	
					transaction = new Transaction(UUID.randomUUID().toString(),moneyTransferReq.getDescription(),new Date(System.currentTimeMillis()),
							Constants.SUCCESS_TRANSACTION, moneyTransferReq.getAmount(),beneficiary.getAccountNumber());

					if(Datastore.getInstance().getAccountTransactionList().get(moneyTransferReq.getAuthenticatedAccountNumber())!=null)
						Datastore.getInstance().getAccountTransactionList().get(moneyTransferReq.getAuthenticatedAccountNumber()).add(transaction);
					else
					{
						List<Transaction> transactionList = new ArrayList<Transaction>();
						transactionList.add(transaction);
						Datastore.getInstance().getAccountTransactionList().put(moneyTransferReq.getAuthenticatedAccountNumber(),transactionList);
					}
					return transaction;
				}
			}
		}

		return transaction;
	}



	public TransferLimit fetchTotalTransactions(int authenticatedAccountNumber)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");		
		BigDecimal totalTodayTransaction = BigDecimal.ZERO;
		TransferLimit transferLimit = new TransferLimit();
		int transactionCount=0;

		if(Datastore.getInstance().getAccountTransactionList().get(authenticatedAccountNumber)==null)
		{
			transferLimit.setMaxNoOfTransactions(transactionCount);
			transferLimit.setDailyLimit(totalTodayTransaction);
			return transferLimit;
		}
		for(Transaction transaction : Datastore.getInstance().getAccountTransactionList().get(authenticatedAccountNumber))
		{
			if(dateFormat.format(transaction.getTimestamp()).equals(dateFormat.format(new Date())))
			{
				totalTodayTransaction = totalTodayTransaction.add(transaction.getAmount());
				transactionCount++;
			}
		}
		transferLimit.setMaxNoOfTransactions(transactionCount);
		transferLimit.setDailyLimit(totalTodayTransaction);
		return transferLimit;
	}


}
