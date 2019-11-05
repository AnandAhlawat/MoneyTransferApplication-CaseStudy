package com.app.revolut.beneficiary.dao;

import com.app.revolut.beneficiary.model.Beneficiary;
import com.app.revolut.beneficiary.util.Datastore;

public class BeneficiaryDao implements IBeneficiaryDao {

	@Override
	public Beneficiary createBeneficiary(Beneficiary beneficiary) throws Exception {		
		Datastore.getInstance().addBeneficiaryData(beneficiary.getBeneficiaryName(), beneficiary);
		return beneficiary;		
	}	
	
	

}
