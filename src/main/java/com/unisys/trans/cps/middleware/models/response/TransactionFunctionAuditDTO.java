package com.unisys.trans.cps.middleware.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * This class is used to get particular columns from database
 */
@Data
@AllArgsConstructor
public class TransactionFunctionAuditDTO {

	private String portalFunction;
	
	private String txnStatus;
	
	 private String status;
	 
	 private String hostError;
}
