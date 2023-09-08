package com.unisys.trans.cps.middleware.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unisys.trans.cps.middleware.models.response.AirlineProfile;

import java.util.List;

@Repository
public interface AirlineProfileRepository extends JpaRepository<AirlineProfile,String> {

//    @Query(value = "SELECT * FROM AIRLINEPROFILE  WHERE CARRIERCODE = ?1 ", nativeQuery = true)
//    AirlineProfile findAirlineByCarrierCode(String carrierCode);

    @Query(value = "SELECT * FROM AIRLINEPROFILE", nativeQuery = true)
    List<AirlineProfile> getAllAirline();
}
