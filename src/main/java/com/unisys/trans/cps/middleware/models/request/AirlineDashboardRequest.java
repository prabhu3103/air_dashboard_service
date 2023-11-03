package com.unisys.trans.cps.middleware.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AirlineDashboardRequest {

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    @NotNull
    private String typeOfInfo;

    @NotNull
    private String areaBy;

    @NotNull
    private String location;

    @NotNull
    private  String carrier;
}
