package com.app.revolut.beneficiary.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.revolut.beneficiary.model.Account;
import com.app.revolut.beneficiary.model.Beneficiary;

import lombok.Getter;


public class Datastore {

	private static Datastore instance;
	
	@Getter
	private static List<Account> accounts = new ArrayList<Account>();
	
	@Getter
	private Map<Integer, List<Beneficiary>> beneficiaryDataForAuthAccnt = Collections.synchronizedMap(new HashMap<Integer, List<Beneficiary>>());	

	private Datastore(){	
	}
	
	static {
		//Few sample accounts for test purpose
		accounts.add(new Account(92500,"Micheal",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92501,"Phillips",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92502,"Sundar",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92503,"Kelly",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92504,"Molly",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92505,"Jay",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92506,"Raven",new BigDecimal(1000),"LYDS-5782"));
		accounts.add(new Account(92507,"Monika",new BigDecimal(1000),"LYDS-5782"));		
	}

	synchronized public static Datastore getInstance() {
		if(instance==null)
			instance = new Datastore();		
		return instance;
	}	

	public Beneficiary addBeneficiaryData(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception{
		if(this.beneficiaryDataForAuthAccnt.get(authenticatedAccountNumber)!=null && 
				this.beneficiaryDataForAuthAccnt.get(authenticatedAccountNumber).contains(new Beneficiary(beneficiary.getAccountNumber())))				
			beneficiary.addError(ErrorCodeList.BENEFECIARY_ALREADY_ADDED);		
		else
		{
			List<Beneficiary> beneficiaryList;
			if(this.beneficiaryDataForAuthAccnt.get(authenticatedAccountNumber)==null) {
				beneficiaryList = new ArrayList<Beneficiary>();
				beneficiaryList.add(beneficiary);

			} else
			{
				beneficiaryList = this.beneficiaryDataForAuthAccnt.get(authenticatedAccountNumber);
				beneficiaryList.add(beneficiary);				
			}
			this.beneficiaryDataForAuthAccnt.put(authenticatedAccountNumber, beneficiaryList);
		}
		return beneficiary;
	}
}
