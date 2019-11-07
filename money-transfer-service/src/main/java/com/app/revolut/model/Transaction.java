package com.app.revolut.model;

import java.math.BigDecimal;
import java.util.Date;

import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction extends RmtErrors{
	
	String transactionId;
	String transactionDescription;
	Date timestamp;
	String status;
	BigDecimal amount;
	int beneficiaryAccountNumber;
	
	public Transaction()
	{
		
	}
	
	public Transaction(String transactionId, String transactionDescription, Date timestamp, String status,
			BigDecimal amount, int beneficiaryAccountNumber) {
		super();
		this.transactionId = transactionId;
		this.transactionDescription = transactionDescription;
		this.timestamp = timestamp;
		this.status = status;
		this.amount = amount;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

}
