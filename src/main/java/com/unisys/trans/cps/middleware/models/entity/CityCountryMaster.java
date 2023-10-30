package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(name = "CITYCOUNTRYMASTER")
@IdClass(CityCountryMasterId.class)
@Entity

public class CityCountryMaster implements Serializable {

    @Id
    @Column(name = "CODE")
    private String code;

    @Id
    @Column(name = "CITYCODE")
    private String cityCode;

    @Column(name = "COUNTRYCODE")
    private String countryCode;

    @Column(name = "CONTINENT")
    private String continent;
}
