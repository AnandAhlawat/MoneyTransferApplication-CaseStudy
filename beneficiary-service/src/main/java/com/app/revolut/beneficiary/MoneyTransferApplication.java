package com.app.revolut.beneficiary;

import static spark.Spark.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.revolut.beneficiary.config.BasicModule;
import com.app.revolut.beneficiary.model.Beneficiary;
import com.app.revolut.beneficiary.model.ResponseBuilder;
import com.app.revolut.beneficiary.service.IBeneficiaryService;
import com.app.revolut.beneficiary.util.RmtException;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;

public class BeneficiaryApplication {

	private final IBeneficiaryService beneficiaryService;	
	private final static Logger LOG = LoggerFactory.getLogger(BeneficiaryApplication.class.getCanonicalName());
	private final static int PORT = 9999;

	@Inject
	BeneficiaryApplication(final IBeneficiaryService beneficiaryService)
	{
		this.beneficiaryService = beneficiaryService;
	}

	void run(final int port) {
		port(port);

		before("/*", (req, res) -> LOG.info(String.format("%s: %s", req.requestMethod(), req.uri())));

		get("healthCheck", (req,res)->"I am up!!");

		post("/beneficiaries", (req,res)->{ 
			res.type("application/json");
			String respString;
			try {
				Beneficiary beneficiary = new Gson().fromJson(req.body(), Beneficiary.class);
				respString = ResponseBuilder.toSuccessJson(beneficiaryService.addBeneficiary(beneficiary));
			} catch(RmtException e)
			{
				res.status(400);
				respString = ResponseBuilder.toExceptionJson(e.getErrorCode(),e);
			}
			return respString;
		});		
		after("/*", (req, res) -> LOG.info(res.body()));
	}


	public static void main(String[] args) {
		Guice.createInjector(new BasicModule())
		.getInstance(BeneficiaryApplication.class)
		.run(PORT);
	}
}
