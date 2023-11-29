package com.unisys.trans.cps.middleware.services.transactionerrorservice;

import com.unisys.trans.cps.middleware.models.request.TransactionRequest;
import com.unisys.trans.cps.middleware.models.response.TransactionData;

public interface TransactionErrorService {

	 public TransactionData getTransactionErrors(TransactionRequest request);
	 
}
