package com.unisys.trans.cps.middleware.models.response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * This Class which is used to display portalFunction transaction data for last 30 days
 * 
 * @author Vishal.Bhat2@unisysm.com
 */
@Data
@Component
public class TransactionData {
	
	private long totalTransaction;

	private long normalCount;

	private long errorCount;

	private Float normalCountPercent;

	private Float errorCountPercent;

	@Autowired
	private List<TransactionErrorData> errorTransactions;

}
