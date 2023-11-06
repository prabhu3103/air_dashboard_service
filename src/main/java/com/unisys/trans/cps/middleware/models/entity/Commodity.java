package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@IdClass(CommodityId.class)
@Data
@Table(name = "COMMODITY")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {

    @Id
    @Column(name = "CARRIER")
    private String carrier;

    @Id
    @Column(name = "CODE")
    private String code;

    @Id
    @Column(name = "DESCRIPTION")
    private String description;
}
