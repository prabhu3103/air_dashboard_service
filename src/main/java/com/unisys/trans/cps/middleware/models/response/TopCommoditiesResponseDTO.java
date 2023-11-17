package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class TopCommoditiesResponseDTO {

    private String valueType;

    private float value;

    private String commodityDesc;

    private String commodity;

    private String unit;

    private float momData;

    private float yoyData;

}
