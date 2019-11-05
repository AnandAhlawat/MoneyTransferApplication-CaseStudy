package com.app.revolut.beneficiary.dao;

import com.app.revolut.beneficiary.model.Beneficiary;

public interface IBeneficiaryDao {
	
	Beneficiary createBeneficiary(Beneficiary beneficiary) throws Exception;

}
