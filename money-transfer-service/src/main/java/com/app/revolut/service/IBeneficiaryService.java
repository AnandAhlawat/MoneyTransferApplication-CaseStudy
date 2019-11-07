package com.app.revolut.service;

import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.UpdateTransferLimitReq;
import com.app.revolut.model.VerifyBeneficiaryReq;

public interface IBeneficiaryService {
	
	public Beneficiary addBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception;
	
	public VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiary) throws Exception;
	
	public UpdateTransferLimitReq updateTransferLimit(UpdateTransferLimitReq updateTransferLimit) throws Exception;

}
