package com.app.revolut.beneficiary.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.app.revolut.beneficiary.model.Beneficiary;

public class Datastore {

	private static Datastore instance;

	private Map<String, Beneficiary> beneficiaryData = Collections.synchronizedMap(new HashMap<String, Beneficiary>());	

	private Datastore(){	
	}

	synchronized public static Datastore getInstance() {
		if(instance==null)
			instance = new Datastore();		
		return instance;
	}

	public Map<String, Beneficiary> getBeneficiaryData() {
		return beneficiaryData;
	}

	public void addBeneficiaryData(String name, Beneficiary beneficiary) throws Exception{
		if(this.beneficiaryData.containsKey(beneficiary.getBeneficiaryName()))
			throw new RmtException(101,"The beneficiary is already added to the current account");
		this.beneficiaryData.put(name, beneficiary);
	}
}
