package com.app.revolut.beneficiary.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBeneficiaryReq {
	
	int authenticatedAccountNumber;	
	Beneficiary beneficiaryDetails;

}
