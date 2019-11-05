package com.app.revolut.beneficiary.service;

import com.app.revolut.beneficiary.dao.IBeneficiaryDao;
import com.app.revolut.beneficiary.model.Beneficiary;
import com.google.inject.Inject;


public class BeneficiaryService implements IBeneficiaryService{

	private final IBeneficiaryDao beneficiaryDao;
	
	@Inject
	BeneficiaryService(IBeneficiaryDao beneficiaryDao)
	{
		this.beneficiaryDao = beneficiaryDao;
	}

	@Override
	public Beneficiary addBeneficiary(Beneficiary beneficiary) throws Exception {		
		return beneficiaryDao.createBeneficiary(beneficiary);					
	}


}
 