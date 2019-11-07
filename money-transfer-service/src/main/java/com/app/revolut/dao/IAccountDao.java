package com.app.revolut.dao;

import com.app.revolut.model.Account;
import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.MoneyTransferReq;
import com.app.revolut.model.Transaction;

public interface IAccountDao {

	Account getAccountDetails(int authenticatedAccountId);

	Transaction transferMoney(MoneyTransferReq moneyTransferReq,Beneficiary benefeciary) throws Exception;
}
