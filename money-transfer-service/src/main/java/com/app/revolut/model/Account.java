package com.app.revolut.model;

import java.math.BigDecimal;

import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account extends RmtErrors{	
	
	private int accountNumber;
	private String accountOwnerName;	
	private BigDecimal amount;
	private String bankCode;
	
	public Account()
	{
		
	}
	
	public Account(int accountNumber, String accountOwnerName, BigDecimal amount, String bankCode) {
		super();
		this.accountNumber = accountNumber;
		this.accountOwnerName = accountOwnerName;
		this.amount = amount;
		this.bankCode = bankCode;
	}
	
	public Account(int accountNumber) {
		super();
		this.accountNumber = accountNumber;		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber != other.accountNumber)
			return false;
		return true;
	}

}
