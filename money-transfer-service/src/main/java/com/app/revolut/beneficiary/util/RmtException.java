package com.app.revolut.beneficiary.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RmtException extends Exception{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String errorMessage;

	RmtException(String errorMessage)
	{
		super(errorMessage);
	}
	
	RmtException()
	{
		super("");
	}
}
