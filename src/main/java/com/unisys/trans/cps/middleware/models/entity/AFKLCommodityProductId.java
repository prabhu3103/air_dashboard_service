package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AFKLCommodityProductId {

    private String commodity;

    private String productCode;

    private String status;

    private String productDesc;

    private String carrier;

    private String commodityGroup;

    private String bkgStatus;

    private String commodityCode;

}
