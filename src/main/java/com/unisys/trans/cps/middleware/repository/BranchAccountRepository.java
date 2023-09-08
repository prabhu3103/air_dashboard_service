package com.unisys.trans.cps.middleware.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unisys.trans.cps.middleware.models.response.BranchAccount;
import com.unisys.trans.cps.middleware.models.response.BranchAccountId;

import java.util.List;

@Repository
public interface BranchAccountRepository extends JpaRepository<BranchAccount, BranchAccountId> {

//    @Query(value = "SELECT * FROM BRANCHACCOUNT  WHERE BRANCHID = ?1 AND carrierCode = ?2", nativeQuery = true)
//    List<BranchAccount> findBranchAccountByBranchIdCarrierCode(int branchId, String carrierCode);


    @Query(value = "SELECT * FROM BRANCHACCOUNT  WHERE BRANCHID = ?1", nativeQuery = true)
    List<BranchAccount> findBranchAccountByBranchId(int branchId);

}
