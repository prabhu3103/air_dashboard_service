package com.unisys.trans.cps.middleware.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unisys.trans.cps.middleware.models.entity.TransactionFunctionAudit;

/**
 * @author Vishal.Bhat2@unisysm.com
 * @version V1.0
 */
@Repository
public interface TransactionErrorRepository extends JpaRepository<TransactionFunctionAudit, Long> {

	String SQL_NAMED_QUERY = "SELECT tfa FROM TransactionFunctionAudit tfa " +
			  "WHERE tfa.eventDate  <= (?2) "
	            + "AND tfa.eventDate >= (?3) " 
	            + "AND tfa.carrier IN (?1)"
	            + "AND tfa.portalFunction IN(?4)";
	@Query(value = SQL_NAMED_QUERY)
	public List<TransactionFunctionAudit> getAllTransactionErrorsData(String carrier, LocalDateTime todayDate,
				LocalDateTime past30Date, String portalFunction);
	      
	    
    String SQL_NAMED_QUERY_COUNT = "SELECT tfa FROM TransactionFunctionAudit tfa " +
            "WHERE tfa.eventDate  <= (?2) "
            + "AND tfa.eventDate >= (?3) " 
            + "AND tfa.carrier IN (?1)"
            + "AND tfa.portalFunction NOT IN(?4)";
    @Query(value = SQL_NAMED_QUERY_COUNT )
	public List<TransactionFunctionAudit> getAllTransactionErrorsCount(String carrier, LocalDateTime todayDate,
			LocalDateTime past30Date, String portalFunction);
}
