package com.unisys.trans.cps.middleware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.TransactionRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TransactionData;
import com.unisys.trans.cps.middleware.services.transactionerrorservice.TransactionErrorService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
/**
 * This is controller class used to fetch Transaction errors and inquiries from database for last 30 days
 * @author Vishal.Bhat2@unisysm.com
 * 
 */
@Slf4j
@RestController
@RequestMapping("/v1/airline-dashboard")
public class TransactionErrorController {

	@Autowired
	private TransactionErrorService transactionErrorService;

	/**
	 * This controller class method is used to fetch Transaction errors from database for last 30 days
	 * @param request
	 * @return TransactionData
	 */
	@PostMapping(value = "/transaction-error-count", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  ResponseEntity<TransactionData> getTransactionErrors( @AuthenticationPrincipal Jwt principal,
			@Valid @RequestBody TransactionRequest request) {
		 log.info("Inside Controller class getTransactionErrors method");
		 ResponseEntity<TransactionData> response=new ResponseEntity<>();
		 TransactionData transactionData=new TransactionData();

	        try {
	        	TransactionData transactionErrorData = transactionErrorService.getTransactionErrors(request);

	            transactionData.setErrorCount(transactionErrorData.getErrorCount());
	            transactionData.setErrorCountPercent(transactionErrorData.getErrorCountPercent());
	            transactionData.setNormalCount(transactionErrorData.getNormalCount());
	            transactionData.setNormalCountPercent(transactionErrorData.getNormalCountPercent());
	            transactionData.setTotalTransaction(transactionErrorData.getTotalTransaction());
	            transactionData.setErrorTransactions(transactionErrorData.getErrorTransactions());
	            transactionData.setPortalFunction(request.getPortalFunction());
	            response.setResponse(transactionData);
	            
	            response.setSuccessFlag(true);
	        } catch (CpsException e) {
	            response.setSuccessFlag(false);
	            response.setErrorList(List.of(new MessageEntry("ERR009", "ERR", e.getMessage())));
	        } catch (Exception e) {
	            response.setSuccessFlag(false);
	            response.setErrorList(List.of(new MessageEntry("ERR010", "ERR", e.getMessage())));
	        }
		return response;
		
	}
}
