package com.app.revolut;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.revolut.config.RevolutConfig;
import com.app.revolut.model.Account;
import com.app.revolut.model.AddBeneficiaryReq;
import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.GetAccountDetailsReq;
import com.app.revolut.model.MoneyTransferReq;
import com.app.revolut.model.ResponseBuilder;
import com.app.revolut.model.Transaction;
import com.app.revolut.model.UpdateTransferLimitReq;
import com.app.revolut.model.VerifyBeneficiaryReq;
import com.app.revolut.service.IAccountService;
import com.app.revolut.service.IBeneficiaryService;
import com.app.revolut.util.ErrorCodeList;
import com.app.revolut.util.RmtException;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;

public class MoneyTransferApplication {

	private final IBeneficiaryService beneficiaryService;	
	final IAccountService accountService;

	private final static Logger LOG = LoggerFactory.getLogger(MoneyTransferApplication.class.getCanonicalName());
	private final static int PORT = 9999;

	@Inject
	MoneyTransferApplication(final IBeneficiaryService beneficiaryService, final IAccountService accountService)
	{
		this.beneficiaryService = beneficiaryService;
		this.accountService = accountService;
	}

	void run(final int port) {
		port(port);

		before("/*", (req, res) -> LOG.info(String.format("%s: %s", req.requestMethod(), req.uri())));		

		initialiseBeneficiaryEndpoints();

		initialiseLimitEndpoints();

		initialiseAccountEndpoints();

		after("/*", (req, res) -> LOG.info(res.body()));
	}


	public static void main(String[] args) {
		Guice.createInjector(new RevolutConfig())
		.getInstance(MoneyTransferApplication.class)
		.run(PORT);
	}


	void initialiseBeneficiaryEndpoints()
	{

		// Add a beneficiary End Point
		post("/beneficiaries", (req,res)->{ 
			res.type("application/json");
			String respString=null;
			try {
				AddBeneficiaryReq beneficiaryDetails = new Gson().fromJson(req.body(), AddBeneficiaryReq.class);
				Beneficiary beneficiary = beneficiaryService.addBeneficiary(beneficiaryDetails.getAuthenticatedAccountNumber(), beneficiaryDetails.getBeneficiaryDetails());				
				if(beneficiary.hasErrors())
				{
					res.status(400);
					respString = ResponseBuilder.toResponseJson(beneficiary.getRmtErrors());
				} else
				{
					respString = ResponseBuilder.toResponseJson(beneficiary);
				}
			} catch(RmtException e)
			{
				res.status(500);
				respString = ResponseBuilder.toExceptionJson(ErrorCodeList.INTERNAL_ERROR_CODE,e);
			}
			return respString;
		});	

		// Verify a beneficiary End Point
		put("/beneficiaries/verify", (req,res)->{ 
			res.type("application/json");
			String respString=null;
			try {
				VerifyBeneficiaryReq verifyBeneficiary = new Gson().fromJson(req.body(), VerifyBeneficiaryReq.class);
				verifyBeneficiary = beneficiaryService.verifyBeneficiary(verifyBeneficiary);				
				if(verifyBeneficiary.hasErrors())
				{
					res.status(400);
					respString = ResponseBuilder.toResponseJson(verifyBeneficiary.getRmtErrors());
				} else
				{
					respString = ResponseBuilder.toResponseJson(verifyBeneficiary);
				}
			} catch(RmtException e)
			{
				res.status(500);
				respString = ResponseBuilder.toExceptionJson(ErrorCodeList.INTERNAL_ERROR_CODE,e);
			}
			return respString;
		});
	}


	void initialiseLimitEndpoints()
	{		
		// Update Transfer limit End Point
		put("/beneficiaries/UpdateTransferlimit", (req,res)->{ 
			res.type("application/json");
			String respString=null;
			try {
				UpdateTransferLimitReq updateTransferLimitReq = new Gson().fromJson(req.body(), UpdateTransferLimitReq.class);
				updateTransferLimitReq = beneficiaryService.updateTransferLimit(updateTransferLimitReq);				
				if(updateTransferLimitReq.hasErrors())
				{
					res.status(400);
					respString = ResponseBuilder.toResponseJson(updateTransferLimitReq.getRmtErrors());
				} else
				{
					respString = ResponseBuilder.toResponseJson(updateTransferLimitReq);
				}
			} catch(RmtException e)
			{
				res.status(500);
				respString = ResponseBuilder.toExceptionJson(ErrorCodeList.INTERNAL_ERROR_CODE,e);
			}
			return respString;
		});
	}

	void initialiseAccountEndpoints()
	{		
		// Get Account Details End Point
		post("/accounts/checkBalance", (req,res)->{ 
			res.type("application/json");
			String respString=null;
			try {
				GetAccountDetailsReq getAccountDetailsReq = new Gson().fromJson(req.body(), GetAccountDetailsReq.class);						
				Account account = accountService.getAccountDetails(getAccountDetailsReq);						
				if(account.hasErrors())
				{
					res.status(400);
					respString = ResponseBuilder.toResponseJson(account.getRmtErrors());
				} else
				{
					respString = ResponseBuilder.toResponseJson(account);
				}
			} catch(RmtException e)
			{
				res.status(500);
				respString = ResponseBuilder.toExceptionJson(ErrorCodeList.INTERNAL_ERROR_CODE,e);
			}
			return respString;
		});	


		// Transfer Money End Point
		post("/accounts/transfer", (req,res)->{ 
			res.type("application/json");
			String respString=null;
			try {
				MoneyTransferReq moneyTransferReq = new Gson().fromJson(req.body(), MoneyTransferReq.class);						
				Transaction transaction = accountService.transerMoney(moneyTransferReq);						
				if(transaction.hasErrors())
				{
					res.status(400);
					respString = ResponseBuilder.toResponseJson(transaction.getRmtErrors());
				} else
				{
					respString = ResponseBuilder.toResponseJson(transaction);
				}
			} catch(RmtException e)
			{
				res.status(500);
				respString = ResponseBuilder.toExceptionJson(ErrorCodeList.INTERNAL_ERROR_CODE,e);
			}
			return respString;
		});	

	}
}
