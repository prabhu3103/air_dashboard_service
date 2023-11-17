package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class TopLanesResponseDTO {

    private String origin;

    private String destination;

    private String valueType;

    private float value;

    private String unit;

    private String orgName;

    private String destName;

    private float momData;

    private float yoyData;

}
