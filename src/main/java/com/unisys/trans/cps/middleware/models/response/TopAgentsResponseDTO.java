package com.unisys.trans.cps.middleware.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopAgentsResponseDTO {

    private String accNo;

    private int branchId;

    private String carrier;

    private String accountDescription;

    private int noOfBookings;
}
