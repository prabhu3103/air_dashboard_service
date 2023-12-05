package com.unisys.trans.cps.middleware.services.transactionerrorservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.TransactionFunctionAudit;
import com.unisys.trans.cps.middleware.models.request.TransactionRequest;
import com.unisys.trans.cps.middleware.models.response.TransactionData;
import com.unisys.trans.cps.middleware.models.response.TransactionErrorCount;
import com.unisys.trans.cps.middleware.models.response.TransactionErrorData;
import com.unisys.trans.cps.middleware.repository.TransactionErrorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This serviceImpl class is used to calculate total transaction and inquiries
 * and depending on errors to calculate count and percentage
 * 
 * @author Vishal.Bhat2@unisysm.com
 */
@Slf4j
@Service
public class TransactionErrorServiceImpl implements TransactionErrorService {

	private TransactionErrorRepository transactionErrorRepository;

	public TransactionErrorServiceImpl(TransactionErrorRepository transactionErrorRepository) {
		super();
		this.transactionErrorRepository = transactionErrorRepository;
	}
	
	/**
	 * This method of serviceImpl class is used to get all transaction errors of particular carrier from database
	 * calculate total transaction count,errorCount and successCount and their respective percentage
	 * Getting status="F"& " " And "E"&"S" as error from FUNCTIONAUDIT table to get errorCount and description for respective status
	 * @param portalFunction
	 * @param request
	 * @return TransactionData
	 */

	@Override
	public TransactionData getTransactionErrors(final TransactionRequest request) {
		String getDate = request.getDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime currentDate = LocalDateTime.parse(getDate, formatter);
		LocalDateTime past30Date = currentDate.minus(30, ChronoUnit.DAYS);
		// Getting all transaction data from database
		List<TransactionFunctionAudit> transactionFunctionAuditList = transactionErrorRepository
				.getAllTransactionErrorsData(request.getCarrier(), currentDate, past30Date,
						request.getPortalFunction());
		
		 List<TransactionErrorCount> transactionErrorCount=getTransctionErrorCount(request.getCarrier(), currentDate, past30Date);
		
		 List<TransactionErrorData> transactionErrorData=getTransactionErrorData(transactionFunctionAuditList);
		// Getting errorCount and successCount to calculate totalTransactions and respective percentage
		TransactionData transactionDataCount = new TransactionData();
		long totalErrorCount = getErrorCount(transactionFunctionAuditList);
		
		long successCount = transactionFunctionAuditList.stream()
				.filter(item -> " ".equals(item.getTxnStatus()) && "S".equals(item.getStatus())).count();
		// Calculating total transactions by adding successCount and errorCount
		long totalTransactions = successCount + totalErrorCount;
		

		List<TransactionData> bookingDataList = new ArrayList<>();
		try {
			// Calculating normalCountPercent and errorCountPercent from the totalTransactions we get
			if (!transactionFunctionAuditList.isEmpty()) {
				float normalCountPercent = totalTransactions > 0 ? (float) (successCount * 100) / totalTransactions: 0.0f;
				float errorCountPercent = totalTransactions > 0 ? (float) (totalErrorCount * 100) / totalTransactions : 0.0f;
				transactionDataCount.setNormalCountPercent(normalCountPercent);
				transactionDataCount.setErrorCountPercent(errorCountPercent);
			} else {
				transactionDataCount.setNormalCountPercent(0.0f);
				transactionDataCount.setErrorCountPercent(0.0f);
			}
		} catch (CpsException cpsException) {
			log.info("Cps Exception in TransactionErrorServiceImpl :", cpsException.getMessage());
			throw cpsException;
		}

		transactionDataCount.setTotalTransaction(totalTransactions);
		transactionDataCount.setNormalCount(successCount);
		transactionDataCount.setErrorCount(totalErrorCount);
		transactionDataCount.setErrorTransactions(transactionErrorData);
		transactionDataCount.setTransactionErrorCount(transactionErrorCount);
		transactionDataCount.setPortalFunction(request.getPortalFunction());
		bookingDataList.add(transactionDataCount);
		return transactionDataCount;
	}

	/**
	 * This method is used to calculate ErrorCount,ErrorDescription & ErrorPercent
	 * depending upon status="E"&"S" And " "&"F"
	 * @param transactionFunctionAuditList
	 * @return List<TransactionErrorData>
	 */
	private List<TransactionErrorData> getTransactionErrorData(final List<TransactionFunctionAudit> transactionFunctionAuditList) {
		// Getting status="F"& " " And "E"&"S" as error from FUNCTIONAUDIT table to get errorCount and description for respective status
		long totalErrorCount = getErrorCount(transactionFunctionAuditList);
		
		return transactionFunctionAuditList.stream()
	            .filter(audit -> ("E".equals(audit.getTxnStatus()) && "S".equals(audit.getStatus()))
	                    || (" ".equals(audit.getTxnStatus()) && "F".equals(audit.getStatus())))
	            .collect(Collectors.groupingBy(TransactionFunctionAudit::getHostError, Collectors.counting()))
	            .entrySet().stream().map(entry -> {
	            	// Logic for every transaction error
	                TransactionErrorData transactionErrorData = new TransactionErrorData();
	                float errorPercent = (float) entry.getValue().intValue() * 100 / totalErrorCount;
	                transactionErrorData.setErrorCount(entry.getValue().intValue());
	                transactionErrorData.setErrorDescription(entry.getKey());
	                transactionErrorData.setErrorPercent(errorPercent);
	                return transactionErrorData;
	            }).toList();
	}
	/**
	 * This Method is used to fetch total error count for particular carrier and 
	 * portalFunction based on their status
	 * @param transactionFunctionAuditList
	 * @return
	 */
	private long getErrorCount(final List<TransactionFunctionAudit> transactionFunctionAuditList) {
		
		return transactionFunctionAuditList.stream()
				.filter(item -> ("E".equals(item.getTxnStatus()) && "S".equals(item.getStatus()))
						|| (" ".equals(item.getTxnStatus()) && "F".equals(item.getStatus()))).count();
	}
	
	/**
	 * This method is used to get error count for particular portalFunction 
	 * to populate on UI on the refresh of page
	 * @param carrier
	 * @param todayDate
	 * @param past30Date
	 * @param portalFunction
	 * @return
	 */
	private List<TransactionErrorCount> getTransctionErrorCount(String carrier, LocalDateTime todayDate,
			LocalDateTime past30Date) {
		// Getting error count for portal function depending on txnStatus and Status
		List<TransactionFunctionAudit> transactionFunctionAuditList = transactionErrorRepository
				.getAllTransactionErrorsCount(carrier, todayDate, past30Date);

		return transactionFunctionAuditList.stream()
				.filter(audit -> ("E".equals(audit.getTxnStatus()) && "S".equals(audit.getStatus()))
						|| (" ".equals(audit.getTxnStatus()) && "F".equals(audit.getStatus())))
				.collect(Collectors.groupingBy(TransactionFunctionAudit::getPortalFunction, Collectors.counting()))
				.entrySet().stream().map(entry -> {
					TransactionErrorCount transactionErrorCount = new TransactionErrorCount();

					transactionErrorCount.setPortalFunction(entry.getKey());
					transactionErrorCount.setErrorCount(entry.getValue().intValue());

					return transactionErrorCount;
				}).toList();
	}
	}
