package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.BranchAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRequestsRepository extends JpaRepository<BranchAccount, Integer > {

    @Query("SELECT status  FROM   BranchAccount WHERE  carrierCode = ?1 ")
    public List<String> getStatus(String carrierCode);

}
