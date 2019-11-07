package com.app.revolut.service;

import com.app.revolut.dao.IAccountDao;
import com.app.revolut.dao.IBeneficiaryDao;
import com.app.revolut.model.Account;
import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.GetAccountDetailsReq;
import com.app.revolut.model.MoneyTransferReq;
import com.app.revolut.model.Transaction;
import com.app.revolut.util.ErrorCodeList;
import com.google.inject.Inject;

public class AccountService implements IAccountService {

	final IAccountDao accountDao;

	final IBeneficiaryDao benefeciaryDao;

	@Inject
	AccountService(IAccountDao accountDao,IBeneficiaryDao benefeciaryDao)
	{
		this.benefeciaryDao = benefeciaryDao;
		this.accountDao = accountDao;
	}

	@Override
	public Transaction transerMoney(MoneyTransferReq moneyTransferReq) throws Exception{	
		Beneficiary benefeciary = benefeciaryDao.getLinkedBenefeciary(moneyTransferReq.getAuthenticatedAccountNumber(),
				moneyTransferReq.getBeneficiaryAccountNumber(),moneyTransferReq);
		return accountDao.transferMoney(moneyTransferReq,benefeciary);
	}

	@Override
	public Account getAccountDetails(GetAccountDetailsReq getAccountDetailsReq) throws Exception {	
		Account account = accountDao.getAccountDetails(getAccountDetailsReq.getAuthenticatedAccountNumber());
		if(account==null) 
		{	
			account = new Account();
			account.addError(ErrorCodeList.INVALID_ACCOUNT);
		}
		return account;
	}

}
