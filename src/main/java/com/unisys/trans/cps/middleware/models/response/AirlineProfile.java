package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class AirlineProfile {

    @Id
    @Column(name = "CARRIERCODE", length = 5)
    private String carrierCode;

    @Column(name = "ACNNUMBER", length = 3)
    private String accountNumber;

    @Column(name = "EMAILBRANCHREQUEST")
    private String emailBranchRequest;

    @Column(name = "CARRIERNAME")
    private String carrierName;

    @Column(name = "REFNUMBER")
    private int refNumber;

    @Column(name = "STARTDATE")
    private LocalDateTime startDate;

    @Column(name = "CONTACTUSERID")
    private String contactUserID;

    @Column(name = "EMAILTRACKING")
    private String emailTracking;

    @Column(name = "EMAILRATINGGROUP")
    private String emailRatingGroup;

    @Column(name = "EMAILCONTACTUS")
    private String emailContactUs;

    @Column(name = "CONTACTPHONE")
    private String contactPhone;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "COUNTRYCODE")
    private String countryCode;

    @Column(name = "MAXBKGSEGMENTS")
    private byte maxBkgSegments;

    @Column(name = "MAXPARTICIPANTS")
    private byte maxParticipants;

    @Column(name = "DENSITYFACTOR")
    private float densityFactor;

    @Column(name = "DOMDIMWGTFACTORLB")
    private double domDimWgtFactorLB;

    @Column(name = "DOMDIMWGTFACTORKG")
    private double domDimWgtFactorKG;

    @Column(name = "INTDIMWGTFACTORLB")
    private double intDimWgtFactorLB;

    @Column(name = "INTDIMWGTFACTORKG")
    private double intDimWgtFactorKG;

    @Column(name = "EXPRESSAWB")
    private String expressAwb;

    @Column(name = "GENERALAWB")
    private String generalAwb;

    @Column(name = "SMALLPKGEXPRESS")
    private String smallPkgAwb;

    @Column(name = "MAXULDTYPE")
    private byte maxUldType;

    @Column(name = "OFFICE")
    private String office;

    @Column(name = "DEFAULTCUSTOMERID")
    private String defaultCustomerId;

    @Column(name = "DATERANGE")
    private short dateRange;

    @Column(name = "ULDDROPDOWN")
    private byte uldDropdown;

    @Column(name = "SPOTRATEEMAILID")
    private String spotrateEmailId;

    @Column(name = "SPECIALBOOKINGSEMAIL")
    private String specialBookingsEmailId;

    @Column(name = "BOOKINGSTATUSEMAIL")
    public String bookingStatusEmailIds;

    @Column(name = "SPECIALBOOKINGSEMAIL1")
    public String specialBookingsEmailId1;

}
