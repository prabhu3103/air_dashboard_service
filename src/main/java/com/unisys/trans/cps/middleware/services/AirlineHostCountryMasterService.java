package com.unisys.trans.cps.middleware.services;

import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.repository.AirlineHostCountryMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineHostCountryMasterService {

    @Autowired
    private AirlineHostCountryMasterRepository repository;

    @Cacheable(value = "airlineHostCountryMaster")
    public AirlineHostCountryMaster findByCarrierCode(String carrierCode) {
        List<AirlineHostCountryMaster> records = repository.findByCarrierCode(carrierCode);

        if (!records.isEmpty()) {
            return records.get(0); // Return the first record
        }

        return null; // Return null in case of no records
    }
}
