package com.app.revolut.service;

import com.app.revolut.model.Account;
import com.app.revolut.model.GetAccountDetailsReq;
import com.app.revolut.model.MoneyTransferReq;
import com.app.revolut.model.Transaction;

public interface IAccountService {
	
	Account getAccountDetails(GetAccountDetailsReq getAccountDetailsReq) throws Exception;
	
	Transaction transerMoney(MoneyTransferReq moneyTransferReq) throws Exception;

}
