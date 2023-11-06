package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class TopCommoditiesResponseDTO {

    private String valueType;

    private Long value;

    private String commodityDesc;

    private String commodity;

    private String unit;

}
