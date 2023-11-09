package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.CityCountryMaster;
import com.unisys.trans.cps.middleware.models.entity.CityCountryMasterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityCountryMasterRepository extends JpaRepository<CityCountryMaster, CityCountryMasterId> {

    @Query("SELECT c.code, c.cityDisplayName FROM CityCountryMaster c WHERE c.code IN (:codes)")
    List<Object[]> findByCodes(@Param("codes") List<String> code);
}
