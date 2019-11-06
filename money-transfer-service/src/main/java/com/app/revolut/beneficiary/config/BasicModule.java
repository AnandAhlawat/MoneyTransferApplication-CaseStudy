package com.app.revolut.beneficiary.config;

import com.app.revolut.beneficiary.dao.BeneficiaryDao;
import com.app.revolut.beneficiary.dao.IBeneficiaryDao;
import com.app.revolut.beneficiary.service.BeneficiaryService;
import com.app.revolut.beneficiary.service.IBeneficiaryService;
import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(IBeneficiaryService.class).to(BeneficiaryService.class);
		bind(IBeneficiaryDao.class).to(BeneficiaryDao.class);
	}
}
