package com.unisys.trans.cps.middleware.models.response;

import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * This is class which is used to display transaction errors for last 30 days
 * @author Vishal.Bhat2@unisysm.com
 */
@Data
@Component
public class TransactionErrorData {

	private float errorPercent;

	private String errorDescription;

	private long errorCount;

	
}
