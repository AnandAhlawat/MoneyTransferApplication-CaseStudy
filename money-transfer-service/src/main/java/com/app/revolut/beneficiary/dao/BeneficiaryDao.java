package com.app.revolut.beneficiary.dao;

import java.util.List;

import com.app.revolut.beneficiary.model.Beneficiary;
import com.app.revolut.beneficiary.model.TransferLimit;
import com.app.revolut.beneficiary.model.UpdateTransferLimitReq;
import com.app.revolut.beneficiary.model.VerifyBeneficiaryReq;
import com.app.revolut.beneficiary.util.Datastore;
import com.app.revolut.beneficiary.util.ErrorCodeList;
import com.app.revolut.beneficiary.util.RmtErrors;

public class BeneficiaryDao implements IBeneficiaryDao {

	@Override
	public Beneficiary createBeneficiary(int authenticatedAccountNumber, Beneficiary beneficiary) throws Exception {		
		return Datastore.getInstance().addBeneficiaryData(authenticatedAccountNumber, beneficiary);		
	}

	@Override
	public VerifyBeneficiaryReq verifyBeneficiary(VerifyBeneficiaryReq verifyBeneficiaryReq)
			throws Exception {
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
		if(Datastore.getInstance().getBeneficiaryDataForAuthAccnt().get(authenticatedAccountNo)!=null)
		{
			List<Beneficiary> benfList = Datastore.getInstance().getBeneficiaryDataForAuthAccnt().get(authenticatedAccountNo);
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
			Datastore.getInstance().getBeneficiaryDataForAuthAccnt().put(beneficiaryAccountNo,benfList);
		}else  {
			rmtError.addError(ErrorCodeList.BENEFECIARY_MISSING);
		}
	}

}
