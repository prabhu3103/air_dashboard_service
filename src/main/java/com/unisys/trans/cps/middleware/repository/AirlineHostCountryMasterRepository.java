package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface AirlineHostCountryMasterRepository extends JpaRepository<AirlineHostCountryMaster, String> {
    List<AirlineHostCountryMaster> findByCarrierCode(String carrierCode);
    // You can define custom query methods here if needed{
}
