package com.app.revolut.beneficiary.model;

import com.app.revolut.beneficiary.util.RmtError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseBuilder{
	
	private ResponseBuilder(){		
	}
	
	public static Gson buildGson()
	{
		return new GsonBuilder().setPrettyPrinting().create();
	}
	
	public static String toSuccessJson(Object obj)
	{
		return buildGson().toJson(obj);
	}
	
	public static String toExceptionJson(int errorCode, Exception e)
	{
		return buildGson().toJson(new RmtError(errorCode,e));
	}

}
