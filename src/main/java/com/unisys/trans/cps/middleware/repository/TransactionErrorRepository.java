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

    
    String SQL_NATIVE_QUERY = "SELECT * FROM [dbo].[FUNCTIONAUDIT] " +
            "WHERE EVENTDATE  <= (?2) "
            + "AND EVENTDATE >= (?3) " 
            + "AND CARRIER IN (?1)"
            + "AND PORTALFUNCTION IN(?4)";
    @Query(value = SQL_NATIVE_QUERY, nativeQuery = true)
    public List<TransactionFunctionAudit> getAllTransactionErrorsData(String carrier, LocalDateTime todayDate,LocalDateTime past30Date,String portalFunction);
      
    String SQL_NAMED_QUERY_COUNT = "select tfa from TransactionFunctionAudit tfa " +
            "where tfa.eventDate  <= (?2) "
            + "and tfa.eventDate >= (?3) " 
            + "and tfa.carrier IN (?1)"
            + "and tfa.portalFunction NOT IN(?4)";
    @Query(value = SQL_NAMED_QUERY_COUNT )
    public List<TransactionFunctionAudit> getAllTransactionErrorsCount(String carrier, LocalDateTime todayDate,LocalDateTime past30Date,String portalFunction);
}
