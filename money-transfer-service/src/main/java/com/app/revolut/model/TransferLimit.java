package com.app.revolut.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferLimit {	
	private int maxNoOfTransactions = 2; // Default Limit of 2 transactions / Day
	private BigDecimal dailyLimit = new BigDecimal(500); // Default limit of 500$ / Day
}
