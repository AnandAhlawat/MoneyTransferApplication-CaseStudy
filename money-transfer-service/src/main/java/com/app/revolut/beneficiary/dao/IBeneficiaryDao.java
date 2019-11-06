package com.app.revolut.beneficiary.dao;

import com.app.revolut.beneficiary.model.Beneficiary;
import com.app.revolut.beneficiary.model.UpdateTransferLimitReq;
import com.app.revolut.beneficiary.model.VerifyBeneficiaryReq;

public interface IBeneficiaryDao {
	
	Beneficiary createBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception;
	
	VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiaryReq) throws Exception;
	
	UpdateTransferLimitReq updateTransferLimit(UpdateTransferLimitReq verifyBeneficiaryReq) throws Exception;

}
