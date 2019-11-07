package com.app.revolut.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBeneficiaryReq {
	
	int authenticatedAccountNumber;	
	Beneficiary beneficiaryDetails;

}
