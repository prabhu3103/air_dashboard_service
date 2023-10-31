package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.Column;

public class CityCountryMasterId {
    @Column(name = "CODE")
    private String code;
    @Column(name = "CITYCODE")
    private String cityCode;


}
