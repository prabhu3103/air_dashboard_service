package com.unisys.trans.cps.middleware.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AirlineDashboardRequest {

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private String typeOfInfo;

    @NotNull
    private String areaBy;

    @NotNull
    private String location;

    @NotNull
    private  String carrier;
}
