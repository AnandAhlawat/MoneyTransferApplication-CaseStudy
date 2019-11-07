package com.app.revolut.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RmtError {
	int errorCode;
	String errorMessage;
	
	public RmtError()
	{
	}
	
	public RmtError(int errorCode, String errorMessage)
	{
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public RmtError(int errorCode, Exception e)
	{
		this.errorCode = errorCode;
		this.errorMessage = e.getMessage();
	}
}
