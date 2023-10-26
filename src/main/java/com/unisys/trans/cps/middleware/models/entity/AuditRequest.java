package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Table(name = "AUDITREQUEST")
@Entity
public class AuditRequest implements Serializable {

    @Id
    @Column(name = "REQUESTID")
    private BigInteger requestId;

    @Column(name = "ACCNO")
    private String accNo;

    @Column(name = "BRANCHID")
    private int branchId;

    @Column(name = "CARRIER")
    private String carrier;
}
