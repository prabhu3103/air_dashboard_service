package com.unisys.trans.cps.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.unisys.trans.cps.middleware.models.response.FunctionAudit;
import com.unisys.trans.cps.middleware.models.response.FunctionAuditDB;

import java.util.List;

/**
 * @author Raji
 * @version V1.0
 * @since 31-AUG-23
 */
@Repository
public interface OperationalDashboardRepository extends JpaRepository<FunctionAudit, String> {

    String SQL_NATIVE_QUERY = " WITH TEMP AS \n"
    		+ " (SELECT CARRIER  AS CARRIER,FORMAT (EVENTDATE,'yyyy-MM-dd') AS EVENTDATE \n"
    		+ " FROM [dbo].[FUNCTIONAUDIT] \n"
    		+ " WHERE EVENTDATE  > (select dateadd(day, -14, getdate())) and PORTALFUNCTION = 'BKG' and \n"
    		+ " SUBFUNCTION='CREATE' and BRANCHID = :branchId and  TXNSTATUS != 'E' and STATUS='S' )\n"
    		+ " Select CARRIER,EVENTDATE,count(*) AS NUMBER_OF_BOOKINGS from TEMP  \n"
    		+ " group by EVENTDATE,CARRIER\n"
    		+ " order by CARRIER,EVENTDATE DESC;";
    @Query(value = SQL_NATIVE_QUERY, nativeQuery = true)
    List<FunctionAuditDB> getAllBranchBookings(int branchId);

}
