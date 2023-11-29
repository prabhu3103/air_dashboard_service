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

	private TransactionErrorRepository transactionErrorDescRepository;

	public TransactionErrorServiceImpl(TransactionErrorRepository transactionErrorDescRepository) {
		super();
		this.transactionErrorDescRepository = transactionErrorDescRepository;
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
		List<TransactionFunctionAudit> transactionFunctionAuditList = transactionErrorDescRepository
				.getAllTransactionErrorsData(request.getCarrier(), currentDate, past30Date,
						request.getPortalFunction());
		
		List<TransactionErrorData> transactionDescErrorData=getTransactionErrorData(transactionFunctionAuditList);
		// Getting errorCount and successCount to calculate totalTransactions and respective percentage
		TransactionData transactionDataCount = new TransactionData();
		long errorCount = transactionFunctionAuditList.stream()
				.filter(item -> ("E".equals(item.getTxnStatus()) && "S".equals(item.getStatus()))
						|| (" ".equals(item.getTxnStatus()) && "F".equals(item.getStatus())))
				.count();
		long successCount = transactionFunctionAuditList.stream()
				.filter(item -> " ".equals(item.getTxnStatus()) && "S".equals(item.getStatus())).count();
		// Calculating total transactions by adding successCount and errorCount
		long totalTransactions = successCount + errorCount;
		long normalCount = totalTransactions - errorCount;

		List<TransactionData> bookingDataList = new ArrayList<>();
		try {
			// Calculating normalCountPercent and errorCountPercent from the totalTransactions we get
			if (!transactionFunctionAuditList.isEmpty()) {
				float normalCountPercent = totalTransactions > 0 ? (float) (normalCount * 100) / totalTransactions: 0.0f;
				float errorCountPercent = totalTransactions > 0 ? (float) (errorCount * 100) / totalTransactions : 0.0f;
				transactionDataCount.setNormalCountPercent(normalCountPercent);
				transactionDataCount.setErrorCountPercent(errorCountPercent);
			} else {
				transactionDataCount.setNormalCountPercent(0.0f);
				transactionDataCount.setErrorCountPercent(0.0f);
			}
		} catch (CpsException cpsException) {
			log.info("Cps Exception in TransactionErrorDescServiceImpl :", cpsException.getMessage());
			throw cpsException;
		}

		transactionDataCount.setTotalTransaction(totalTransactions);
		transactionDataCount.setNormalCount(normalCount);
		transactionDataCount.setErrorCount(errorCount);
		transactionDataCount.setErrorTransactions(transactionDescErrorData);
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
		long totalErrorCount = transactionFunctionAuditList.stream()
				.filter(item -> ("E".equals(item.getTxnStatus()) && "S".equals(item.getStatus()))
						|| (" ".equals(item.getTxnStatus()) && "F".equals(item.getStatus()))).count();
		
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
}
