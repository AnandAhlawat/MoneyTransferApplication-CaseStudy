package com.app.revolut.beneficiary.model;

import com.app.revolut.beneficiary.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransferLimitReq extends RmtErrors {

	private int authenticatedAccountNumber;
	private int benefeciaryAccountNumber;
	private TransferLimit transferLimit;
}
