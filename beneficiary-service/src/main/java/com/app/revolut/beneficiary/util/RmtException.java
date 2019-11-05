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
	
	int errorCode;
	
	String errorMessage;
	
	RmtException(int errorCode,String errorMessage)
	{
		super(errorMessage);
	}
}
