package com.app.revolut.model;


import com.app.revolut.util.RmtError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseBuilder{
	
	private ResponseBuilder(){		
	}
	
	public static Gson buildGson()
	{
		return new GsonBuilder().setPrettyPrinting().create();
	}
	
	public static String toResponseJson(Object obj)
	{
		return buildGson().toJson(obj);
	}
		
	public static String toExceptionJson(int errorCode, Exception e)
	{
		return buildGson().toJson(new RmtError(errorCode,e));
	}

}
