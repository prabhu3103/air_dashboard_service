package com.unisys.trans.cps.middleware.models.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BranchAccountId implements Serializable {

    private int branchId;

    private String carrierCode;

    private String accountId;
}
