package com.app.revolut.model;

import java.math.BigDecimal;

import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyTransferReq extends RmtErrors{
	
	int authenticatedAccountNumber;
	int beneficiaryAccountNumber;
	BigDecimal amount;
	String description;
}
