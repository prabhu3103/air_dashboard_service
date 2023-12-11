package com.unisys.trans.cps.middleware.services.transactionerrorservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.models.entity.TransactionFunctionAudit;
import com.unisys.trans.cps.middleware.models.request.TransactionRequest;
import com.unisys.trans.cps.middleware.models.response.TransactionData;
import com.unisys.trans.cps.middleware.models.response.TransactionErrorCount;
import com.unisys.trans.cps.middleware.models.response.TransactionErrorData;
import com.unisys.trans.cps.middleware.repository.TransactionErrorRepository;

/**
 * This serviceImpl class is used to calculate total transaction and inquiries
 * and depending on errors to calculate count and percentage
 * 
 * @author Vishal.Bhat2@unisysm.com
 */

@Service
public class TransactionErrorServiceImpl implements TransactionErrorService {

	private TransactionErrorRepository transactionErrorRepository;

	public TransactionErrorServiceImpl(TransactionErrorRepository transactionErrorRepository) {
		super();
		this.transactionErrorRepository = transactionErrorRepository;
	}
	

	/**
	 * This method of serviceImpl class is used to fetch data for particular portal function
	 * 
	 * @param portalFunction
	 * @param request
	 * @return
	 */
	@Override
	public TransactionData getTransactionErrors(TransactionRequest request) {

		// Date Manipulation for 30 days interval
		String getDate = request.getDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime currentDate = LocalDateTime.parse(getDate, formatter);
		LocalDateTime past30Date = currentDate.minus(30, ChronoUnit.DAYS);
		
		List<String> portalFunctions = Arrays.asList(request.getPortalFunction());
				 
		// Fetching data for portal function-Booking Transaction
		if(portalFunctions.contains(AirlineDashboardConstants.BKG)) {
			portalFunctions = new ArrayList<>(
				Arrays.asList(AirlineDashboardConstants.BKG, AirlineDashboardConstants.TMPLTBKG, AirlineDashboardConstants.MULTIBKG, 
						AirlineDashboardConstants.SSHT, AirlineDashboardConstants.SSHTQ, AirlineDashboardConstants.USSHT, AirlineDashboardConstants.USSHTQ));
		}
		
		List<TransactionFunctionAudit> transactionFunctionAuditList = transactionErrorRepository
				.getAllTransactionErrorsData(request.getCarrier(), currentDate, past30Date, portalFunctions);

		TransactionData transactionsTotalData = getTransactionErrorsCount(transactionFunctionAuditList);

		List<TransactionErrorData> transactionsErrorData = getTransactionErrorData(transactionFunctionAuditList);

		List<TransactionErrorCount> transactionErrorCountForPortalFunctions=getTransactionErrorCountForOtherPortalFunctions(request.getCarrier(), currentDate, past30Date);
		
		transactionsTotalData.setPortalFunction(request.getPortalFunction());
		transactionsTotalData.setErrorTransactions(transactionsErrorData);
		transactionsTotalData.setTransactionErrorCount(transactionErrorCountForPortalFunctions);
		
		return transactionsTotalData;
	}
	
	/**
	 * This method of serviceImpl class is used to get all transaction errors of particular carrier from database
	 * calculate total transaction count,errorCount and successCount and their respective percentage
	 * Getting status="F"& " " And "E"&"S" as error from FUNCTIONAUDIT table to get errorCount and description for respective status
	 * @param portalFunction
	 * @param request
	 * @return TransactionData
	 */

//	@Override
	public TransactionData getTransactionErrorsCount(List<TransactionFunctionAudit> transactionFunctionAuditList) {
		
		 List<TransactionErrorData> transactionErrorData=getTransactionErrorData(transactionFunctionAuditList);
		// Getting errorCount and successCount to calculate totalTransactions and respective percentage
		TransactionData transactionDataCount = new TransactionData();
		long totalErrorCount = getErrorCount(transactionFunctionAuditList);
		
		long successCount = transactionFunctionAuditList.stream()
				.filter(item -> " ".equals(item.getTxnStatus()) && "S".equals(item.getStatus())).count();
		// Calculating total transactions by adding successCount and errorCount
		long totalTransactions = successCount + totalErrorCount;
		
		List<TransactionData> bookingDataList = new ArrayList<>();
		
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
		transactionDataCount.setTotalTransaction(totalTransactions);
		transactionDataCount.setNormalCount(successCount);
		transactionDataCount.setErrorCount(totalErrorCount);
		transactionDataCount.setErrorTransactions(transactionErrorData);
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
	 * @return
	 */
	private List<TransactionErrorCount> getTransactionErrorCountForOtherPortalFunctions(String carrier, LocalDateTime todayDate,
			LocalDateTime past30Date) {
		List<String> portalFunctions=Arrays.asList(AirlineDashboardConstants.BKG, AirlineDashboardConstants.TMPLTBKG, AirlineDashboardConstants.MULTIBKG, 
				AirlineDashboardConstants.SSHT, AirlineDashboardConstants.SSHTQ, AirlineDashboardConstants.USSHT, AirlineDashboardConstants.USSHTQ,AirlineDashboardConstants.MAWB,AirlineDashboardConstants.ADVRATE);
		// Getting error count for portal function depending on txnStatus and Status
		List<TransactionFunctionAudit> transactionFunctionAuditList = transactionErrorRepository
				.getAllTransactionErrorsCount(carrier, todayDate, past30Date, portalFunctions);

		return transactionFunctionAuditList.stream()
				.filter(audit -> ("E".equals(audit.getTxnStatus()) && "S".equals(audit.getStatus()))
						|| (" ".equals(audit.getTxnStatus()) && "F".equals(audit.getStatus())))
				.collect(Collectors.groupingBy(TransactionFunctionAudit::getPortalFunction, Collectors.counting()))
				.entrySet().stream().map(entry -> {
					
					TransactionErrorCount transactionErrorCount = new TransactionErrorCount();
					if(entry.getKey().equals(AirlineDashboardConstants.TMPLTBKG) || entry.getKey().equals(AirlineDashboardConstants.MULTIBKG) || entry.getKey().equals(AirlineDashboardConstants.SSHT) 
							|| entry.getKey().equals(AirlineDashboardConstants.SSHTQ) ||entry.getKey().equals(AirlineDashboardConstants.USSHT) || entry.getKey().equals(AirlineDashboardConstants.USSHTQ) ) 
					{
					transactionErrorCount.setPortalFunction(AirlineDashboardConstants.BKG);
					}
					else {
						transactionErrorCount.setPortalFunction(entry.getKey());
					}
					transactionErrorCount.setErrorCount(entry.getValue().intValue());
					
					return transactionErrorCount;
				}).toList();
	}
	}
