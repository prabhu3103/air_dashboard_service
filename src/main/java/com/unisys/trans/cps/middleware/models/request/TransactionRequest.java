package com.unisys.trans.cps.middleware.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * This class is used to pass as request
 * 
 * @author Vishal.Bhat2@unisysm.com
 */
@Data
public class TransactionRequest {

	@NotNull
	private String date;

	@NotNull
	private String carrier;
	
	@NotNull
	private String portalFunction;
}
