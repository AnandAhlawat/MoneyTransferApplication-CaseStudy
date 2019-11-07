package com.app.revolut.config;

import com.app.revolut.dao.BeneficiaryDao;
import com.app.revolut.dao.IBeneficiaryDao;
import com.app.revolut.dao.IAccountDao;
import com.app.revolut.dao.AccountDao;
import com.app.revolut.service.BeneficiaryService;
import com.app.revolut.service.IBeneficiaryService;
import com.app.revolut.service.IAccountService;
import com.app.revolut.service.AccountService;
import com.google.inject.AbstractModule;

public class RevolutConfig extends AbstractModule{

	@Override
	protected void configure() {
		bind(IBeneficiaryService.class).to(BeneficiaryService.class);
		bind(IBeneficiaryDao.class).to(BeneficiaryDao.class);
		bind(IAccountService.class).to(AccountService.class);
		bind(IAccountDao.class).to(AccountDao.class);
	}
}
