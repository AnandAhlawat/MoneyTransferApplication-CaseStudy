package com.app.revolut.model;


import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransferLimitReq extends RmtErrors {

	private int authenticatedAccountNumber;
	private int benefeciaryAccountNumber;
	private TransferLimit transferLimit;
}
