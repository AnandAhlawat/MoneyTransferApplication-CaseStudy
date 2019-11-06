package com.app.revolut.beneficiary.util;

public class ErrorCodeList {
	public static int INTERNAL_ERROR_CODE = 1000;
	public static String SERVICE_NOT_AVAILABLE = "Service not available";
	
	public static RmtError BENEFECIARY_ALREADY_ADDED = new RmtError(100,"Beneficiary Already added in the current account");
	public static RmtError BENEFECIARY_MISSING = new RmtError(101,"Beneficiary is not added in the account, Please add before verification");

}
