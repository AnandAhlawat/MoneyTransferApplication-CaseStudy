package com.app.revolut.model;



import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beneficiary extends RmtErrors {

	
	private int accountNumber;	
	private String beneficiaryName;	
	private String bankCode;	
	
	private TransferLimit transferLimit;
	
	private boolean verified=false;

	public Beneficiary()
	{

	}

	public Beneficiary(int accountNumber)
	{
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
		Beneficiary other = (Beneficiary) obj;
		if (accountNumber != other.accountNumber)
			return false;
		return true;
	}
	
	
}
