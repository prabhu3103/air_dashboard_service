package com.unisys.trans.cps.middleware.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "FUNCTIONAUDIT")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFunctionAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDITID")
    private long auditID;
    
    @Column(name = "BRANCHID")
    private long branchID;
    
    @Column(name = "EVENTDATE")
    private LocalDateTime eventDate;
    
    @Column(name = "CARRIER")
    private String carrier;
    
    @Column(name = "AWBNUMBER")
    private String awbNumber;
    
    @Column(name = "PORTALFUNCTION")
    private String portalFunction;
    
    @Column(name = "SUBFUNCTION")
    private String subFunction;
    
    @Column(name = "USERID")
    private String userId;
    
    @Column(name = "TXNSTATUS")
    private String txnStatus;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "DOCUMENT",columnDefinition = "TEXT")
    private String document;
    
    @Column(name = "CONFNUMBER")
    private String confNumber;
    
    @Column(name = "SERVERNAME")
    private String serverName;
    
    @Column(name = "ERRORTXT")
    private String errorTxt;
    
    @Column(name = "IPADDRESS")
    private String ipAddress;
    
    @Column(name = "PORTALIDENTITY")
    private String portalIdentity;
    
    @Column(name = "HOSTERROR")
    private String hostError;
    
    @Column(name = "FROMPAGE")
    private String fromPage;


}
