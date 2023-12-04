package com.unisys.trans.cps.middleware.models.response;


import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * This class is used to get the error count for portalFunction 
 * to be populate on UI display on screen refresh
 */
@Data
@Component
public class TransactionErrorCount {

	private String portalFunction; 
	
	private long errorCount;
}
