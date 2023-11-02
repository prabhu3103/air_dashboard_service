package com.unisys.trans.cps.middleware.models.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(name = "BRANCHPROFILE")
@Entity
public class BranchProfile implements Serializable {

    @Id
    @Column(name = "BRANCHID")
    private long branchId;

    @Column(name = "CORPORATION")
    private String corporation;
}
