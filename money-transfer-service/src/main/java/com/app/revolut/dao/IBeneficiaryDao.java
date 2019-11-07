package com.app.revolut.dao;

import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.UpdateTransferLimitReq;
import com.app.revolut.model.VerifyBeneficiaryReq;
import com.app.revolut.util.RmtErrors;

public interface IBeneficiaryDao {
	
	Beneficiary createBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception;
	
	VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiaryReq) throws Exception;
	
	UpdateTransferLimitReq updateTransferLimit(UpdateTransferLimitReq verifyBeneficiaryReq) throws Exception;
	
	Beneficiary getLinkedBenefeciary(int authenticatedAccountId, int benefeciaryAccountId, RmtErrors rmtErrors) throws Exception;

}
