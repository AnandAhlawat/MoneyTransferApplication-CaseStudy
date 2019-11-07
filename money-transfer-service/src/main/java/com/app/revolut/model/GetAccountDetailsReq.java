package com.app.revolut.model;

import com.app.revolut.util.RmtErrors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAccountDetailsReq extends RmtErrors{
	int authenticatedAccountNumber;
}
