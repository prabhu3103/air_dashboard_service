package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@IdClass(BranchAccountId.class)
@Table(name = "BRANCHACCOUNT")
public class BranchAccount implements Serializable {

    @Id
    @Column(name = "BRANCHID")
    private int branchId;

    @Id
    @Column(name = "ACCOUNTID")
    private String accountId;

    @Id
    @Column(name = "CARRIERCODE")
    private String carrierCode;

    @Column(name = "ACCOUNTDESCRIPTION")
    private String accountDescription;

    @Column(name = "ACTIONDATE")
    private LocalDateTime actionDate;

    @Column(name = "STATUS")
    private String status;

}
