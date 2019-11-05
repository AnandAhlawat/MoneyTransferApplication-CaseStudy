package com.app.revolut.beneficiary.model;

import lombok.Getter;
import lombok.Setter;

public class Beneficiary {

	@Getter
	@Setter
	private String accountNumber;

	@Getter
	@Setter
	private String beneficiaryName;

	@Getter
	@Setter
	private String bankCode;
}
