package com.app.revolut.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RmtErrors {

	List<RmtError> rmtErrors = new ArrayList<RmtError>();

	public void addError(RmtError appError)
	{
		rmtErrors.add(appError);
	}

	public boolean hasErrors()
	{
		if(rmtErrors.size()>0)
			return true;
		else return false;
	}

}
