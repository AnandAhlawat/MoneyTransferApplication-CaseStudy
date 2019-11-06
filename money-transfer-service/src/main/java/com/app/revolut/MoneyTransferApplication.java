package com.app.revolut;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.revolut.beneficiary.config.BasicModule;
import com.app.revolut.beneficiary.model.AddBeneficiaryReq;
import com.app.revolut.beneficiary.model.Beneficiary;
import com.app.revolut.beneficiary.model.ResponseBuilder;
import com.app.revolut.beneficiary.model.UpdateTransferLimitReq;
import com.app.revolut.beneficiary.model.VerifyBeneficiaryReq;
import com.app.revolut.beneficiary.service.IBeneficiaryService;
import com.app.revolut.beneficiary.util.ErrorCodeList;
import com.app.revolut.beneficiary.util.RmtException;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;

public class MoneyTransferApplication {

	private final IBeneficiaryService beneficiaryService;	
	private final static Logger LOG = LoggerFactory.getLogger(MoneyTransferApplication.class.getCanonicalName());
	private final static int PORT = 9999;

	@Inject
	MoneyTransferApplication(final IBeneficiaryService beneficiaryService)
	{
		this.beneficiaryService = beneficiaryService;
	}

	void run(final int port) {
		port(port);

		before("/*", (req, res) -> LOG.info(String.format("%s: %s", req.requestMethod(), req.uri())));		

		initialiseBeneficiaryEndpoints();

		initialiseLimitEndpoints();


		after("/*", (req, res) -> LOG.info(res.body()));
	}


	public static void main(String[] args) {
		Guice.createInjector(new BasicModule())
		.getInstance(MoneyTransferApplication.class)
		.run(PORT);
	}

	
	void initialiseBeneficiaryEndpoints()
	{

		// Add a beneficiary endpoint
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

		// Verify a beneficiary endpoint
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
		// Update Transfer limit endpoint
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
}
