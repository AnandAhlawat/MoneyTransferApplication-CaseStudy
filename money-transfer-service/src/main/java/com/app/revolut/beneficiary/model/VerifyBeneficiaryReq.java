package com.app.revolut.beneficiary.model;

import com.app.revolut.beneficiary.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyBeneficiaryReq extends RmtErrors{
	int authenticatedAccountNumber;	
	int beneficiaryAccountNumber;
}
