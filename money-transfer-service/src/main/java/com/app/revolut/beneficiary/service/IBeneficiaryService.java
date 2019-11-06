package com.app.revolut.beneficiary.service;

import com.app.revolut.beneficiary.model.Beneficiary;
import com.app.revolut.beneficiary.model.UpdateTransferLimitReq;
import com.app.revolut.beneficiary.model.VerifyBeneficiaryReq;

public interface IBeneficiaryService {
	
	public Beneficiary addBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception;
	
	public VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiary) throws Exception;
	
	public UpdateTransferLimitReq updateTransferLimit(UpdateTransferLimitReq updateTransferLimit) throws Exception;

}
