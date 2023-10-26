package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.AuditRequest;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AuditRequestRepository extends JpaRepository<AuditRequest, BigInteger> {

    @Query("select a.accNo, a.branchId, a.carrier, b.accountDescription, COUNT(*) as noOfBookings  from AuditRequest a, BranchAccount b where a.accNo=b.accountId and a.branchId=b.branchId and\n" +
            "a.carrier=b.carrierCode \n" +
            "group by a.accNo, a.branchId, a.carrier, b.accountDescription order by noOfBookings desc")
    public List<Object[]> getTopAgents();
}
