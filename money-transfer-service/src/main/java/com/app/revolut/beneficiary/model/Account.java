package com.app.revolut.beneficiary.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {	
	private int accountNumber;
	private String accountOwnerName;	
	private BigDecimal amount;
	private String bankCode;
	
	public Account(int accountNumber, String accountOwnerName, BigDecimal amount, String bankCode) {
		super();
		this.accountNumber = accountNumber;
		this.accountOwnerName = accountOwnerName;
		this.amount = amount;
		this.bankCode = bankCode;
	}
}
