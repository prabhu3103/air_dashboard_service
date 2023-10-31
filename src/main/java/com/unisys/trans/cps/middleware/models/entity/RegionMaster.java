package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Table(name = "REGIONMASTER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RegionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGIONID")
    private BigInteger regionId;

    @Column(name = "REGIONNAME")
    private String regionName;

    @Column(name = "CONTINENT")
    private String continent;
}
