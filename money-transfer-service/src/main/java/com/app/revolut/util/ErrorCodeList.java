package com.app.revolut.util;

public class ErrorCodeList {
	public static int INTERNAL_ERROR_CODE = 1000;
	public static String SERVICE_NOT_AVAILABLE = "Service not available";
	
	public static RmtError BENEFECIARY_ALREADY_ADDED = new RmtError(100,"Beneficiary Already added in the current account");
	public static RmtError BENEFECIARY_MISSING = new RmtError(101,"Beneficiary is not added in the account, Please add before verification");
	
	public static RmtError INVALID_ACCOUNT = new RmtError(102,"There is no information of the mentioned authenticated account in the System");
	public static RmtError INVALID_BENEFICIARY_ACCOUNT = new RmtError(103,"There is no information of the mentioned benefeciary account in the System");
	public static RmtError BENEFECIARY_NOT_LINKED = new RmtError(104,"Beneficiary is not linked to the authenticated account");
	public static RmtError BENEFECIARY_NOT_VERIFIED = new RmtError(105,"Beneficiary is not verified, Please verify before transfering money");
	
	public static RmtError INSUFFICIENT_FUNDS = new RmtError(106,"Authenticated account doesn't have the required funds to do this transaction, Please try with a lower amount");
	public static RmtError MAX_TRANS_PER_DAY_EXCEED = new RmtError(107,"Maximum transactions per day allowed for the benefeciary have been exceeded");
	public static RmtError MAX_AMOUNT_PER_DAY_EXCEED = new RmtError(108,"Maximum amount per day allowed for the benefeciary have been exceeded");
}
