package com.unisys.trans.cps.middleware.models.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Table(name = "AIRLINEHOSTCOUNTRYMASTER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AirlineHostCountryMaster implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "CARRIERCODE")
    private String carrierCode;

    @Column(name = "STDWEIGHTUNIT")
    private String stdWeightUnit;

    @Column(name = "STDVOLUMEUNIT")
    private String stdVolumeUnit;
}
