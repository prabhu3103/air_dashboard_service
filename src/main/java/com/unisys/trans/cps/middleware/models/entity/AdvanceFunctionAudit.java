package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "ADVANCEFUNCTIONAUDIT")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceFunctionAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDITID")
    private long auditID;
    @Column(name = "ORG")
    private String origin;
    @Column(name = "DEST")
    private String destination;
    @Column(name = "VOLUME_UNIT")
    private String volUnit;
    @Column(name = "WEIGHT_UNIT")
    private String weightUnit;
    @Column(name = "SOURCE")
    private String source;
    @Column(name = "SPLIT")
    private String split;
    @Column(name = "TRANSACTIONID")
    private String transactionID ;
    @Column(name = "PRODUCTCODE")
    private String productCode;
    @Column(name = "COMMODITY")
    private String commodity;
    @Column(name = "COMMDESC")
    private String commodityDesc;
    @Column(name = "PRODDESC")
    private String prodDesc;
    @Column(name = "ACCNO")
    private String accNo;
    @Column(name = "ACCTYPE")
    private String accType;
    @Column(name = "ACCNAME")
    private String accName;
    @Column(name = "PCS")
    private int pcs;
    @Column(name = "VOLUME")
    private BigDecimal vol;
    @Column(name = "WEIGHT")
    private BigDecimal weight;
    @Column(name = "STDWEIGHT")
    private BigDecimal stdWeight;
    @Column(name = "STDVOLUME")
    private BigDecimal stdVol;

    //Data from Existing FUCNTIONAUDIT Table
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
