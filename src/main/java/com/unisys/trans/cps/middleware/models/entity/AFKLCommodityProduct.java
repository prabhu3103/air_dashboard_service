package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(AFKLCommodityProductId.class)
@Data
@Table(name = "AFKLCOMMODITYPRODUCT")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AFKLCommodityProduct {

    @Id
    @Column(name = "COMMODITY")
    private String commodity;

    @Id
    @Column(name = "PRODUCTCODE")
    private String productCode;

    @Id
    @Column(name = "STATUS")
    private String status;

    @Id
    @Column(name = "PRODUCTDESC")
    private String productDesc;

    @Id
    @Column(name = "CARRIER")
    private String carrier;

    @Id
    @Column(name = "COMMODITYGROUP")
    private String commodityGroup;

    @Id
    @Column(name = "BKGSTATUS")
    private String bkgStatus;

    @Id
    @Column(name = "COMMODITYCODE")
    private String commodityCode;
}
