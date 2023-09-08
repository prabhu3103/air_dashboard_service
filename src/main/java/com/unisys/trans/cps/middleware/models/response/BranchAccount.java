/*
 * ===========================================================
 *     Copyright (c) 2023. Unisys Corporation.
 *           All rights reserved.
 *           UNISYS CONFIDENTIAL
 * ===========================================================
 */
package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import javax.persistence.*;

import com.unisys.trans.cps.middleware.constants.CPSConstants;

import java.time.LocalDateTime;

@Data
@Entity(name = "BRANCHACCOUNT")
@IdClass(BranchAccountId.class)
public class BranchAccount {

    @Id
    @Column(name = "BRANCHID")
    private int branchId;

    @Id
    @Column(name = "ACCOUNTID")
    private String accountId;

    @Id
    @Column(name = "CARRIERCODE", length = 5)
    private String carrierCode;

    @Column(name = "RELATEDACCOUNTID")
    private String relatedAccountId;

    @Column(name = "ACTIONDATE")
    private LocalDateTime actionDate;

    @Column(name = "STATUS")
    private byte status;

    @Column(name = "ACCOUNTDESCRIPTION")
    private String accountDescription;

    @Column(name = "COLLABORATIONBRANCHID")
    private String collaborationBranchId;

    @PrePersist
    private void setDefaultValues(){

        if (actionDate == null) {
            LocalDateTime dateTime = LocalDateTime.now();
            actionDate = dateTime;
        }

        if (collaborationBranchId == null) {
            collaborationBranchId = CPSConstants.ZERO_STRING;
        }
    }
}
