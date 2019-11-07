package com.app.revolut.service;

import com.app.revolut.dao.IBeneficiaryDao;
import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.UpdateTransferLimitReq;
import com.app.revolut.model.VerifyBeneficiaryReq;
import com.google.inject.Inject;


public class BeneficiaryService implements IBeneficiaryService{

	private final IBeneficiaryDao beneficiaryDao;
	
	@Inject
	BeneficiaryService(IBeneficiaryDao beneficiaryDao)
	{
		this.beneficiaryDao = beneficiaryDao;
	}

	@Override
	public Beneficiary addBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception {		
		return beneficiaryDao.createBeneficiary(authenticatedAccountNumber,beneficiary);					
	}

	@Override
	public VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiaryReq)
			throws Exception {
		return beneficiaryDao.verifyBeneficiary(verifyBeneficiaryReq);	
	}

	@Override
	public UpdateTransferLimitReq updateTransferLimit(UpdateTransferLimitReq updateTransferLimit) throws Exception {
		return beneficiaryDao.updateTransferLimit(updateTransferLimit);
	}


}
 