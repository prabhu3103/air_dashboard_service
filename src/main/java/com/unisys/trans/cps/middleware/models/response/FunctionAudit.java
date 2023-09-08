package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FUNCTIONAUDIT")
public class FunctionAudit {
    @Id
    @Column(name = "AUDITID")
    private String auditId;

}
