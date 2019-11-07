package com.app.revolut.dao;

import java.util.List;

import com.app.revolut.model.Account;
import com.app.revolut.model.Beneficiary;
import com.app.revolut.model.TransferLimit;
import com.app.revolut.model.UpdateTransferLimitReq;
import com.app.revolut.model.VerifyBeneficiaryReq;
import com.app.revolut.util.Datastore;
import com.app.revolut.util.ErrorCodeList;
import com.app.revolut.util.RmtErrors;


public class BeneficiaryDao implements IBeneficiaryDao {

	@Override
	public Beneficiary createBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception {
		if(!Datastore.getInstance().getAccounts().contains(new Account(authenticatedAccountNumber)))
			beneficiary.addError(ErrorCodeList.INVALID_ACCOUNT);
		if(!Datastore.getInstance().getAccounts().contains(new Account(beneficiary.getAccountNumber())))
			beneficiary.addError(ErrorCodeList.INVALID_BENEFICIARY_ACCOUNT);
		else
			beneficiary = Datastore.getInstance().addBeneficiaryData(authenticatedAccountNumber, beneficiary);
		return beneficiary;		
	}

	@Override
	public VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiaryReq)
			throws Exception {
		if(!Datastore.getInstance().getAccounts().contains(new Account(verifyBeneficiaryReq.getAuthenticatedAccountNumber())))
			verifyBeneficiaryReq.addError(ErrorCodeList.INVALID_ACCOUNT);
		if(!Datastore.getInstance().getAccounts().contains(new Account(verifyBeneficiaryReq.getBeneficiaryAccountNumber())))
			verifyBeneficiaryReq.addError(ErrorCodeList.INVALID_BENEFICIARY_ACCOUNT);
		else
			updateBeneficiary(verifyBeneficiaryReq.getAuthenticatedAccountNumber(),verifyBeneficiaryReq.getBeneficiaryAccountNumber(),verifyBeneficiaryReq,null,false);

		return verifyBeneficiaryReq;

	}	

	@Override
	public UpdateTransferLimitReq updateTransferLimit(UpdateTransferLimitReq updateTransferLimitReq) throws Exception{
		updateBeneficiary(updateTransferLimitReq.getAuthenticatedAccountNumber(),updateTransferLimitReq.getBenefeciaryAccountNumber(),
				updateTransferLimitReq,updateTransferLimitReq.getTransferLimit(),true);
		return updateTransferLimitReq;
	}



	private void updateBeneficiary(int authenticatedAccountNo, int beneficiaryAccountNo, RmtErrors rmtError, TransferLimit limit,boolean limitUpdate) {
		if(Datastore.getInstance().getAccountBenefeciaryList().get(authenticatedAccountNo)!=null)
		{
			List<Beneficiary> benfList = Datastore.getInstance().getAccountBenefeciaryList().get(authenticatedAccountNo);
			boolean found = false;
			for (Beneficiary benf : benfList){
				if(benf.getAccountNumber() == beneficiaryAccountNo)
				{
					if(limitUpdate)
						benf.setTransferLimit(limit);
					else
						benf.setVerified(true);
					found = true;
				}
			}
			if(!found)
				rmtError.addError(ErrorCodeList.BENEFECIARY_MISSING);
			Datastore.getInstance().getAccountBenefeciaryList().put(beneficiaryAccountNo,benfList);
		}else  {
			rmtError.addError(ErrorCodeList.BENEFECIARY_MISSING);
		}
	}

	public Beneficiary getLinkedBenefeciary(int authenticatedAccountId, int benefeciaryAccountId, RmtErrors rmtErrors)
	{
		if(Datastore.getInstance().getAccountBenefeciaryList().get(authenticatedAccountId)!=null)
		{
			return Datastore.getInstance().getAccountBenefeciaryList().get(authenticatedAccountId).stream()
					.filter(benefeciary -> benefeciaryAccountId == benefeciary.getAccountNumber())
					.findAny()
					.orElse(null);
		}
		else  {
			rmtErrors.addError(ErrorCodeList.INVALID_ACCOUNT);
			return null;
		}
	}

}
