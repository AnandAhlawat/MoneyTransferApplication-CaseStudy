package com.app.revolut.model;


import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyBeneficiaryReq extends RmtErrors{
	int authenticatedAccountNumber;	
	int beneficiaryAccountNumber;
}
