package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class PointOfSalesResponseDTO {

    private String valueType;

    private Long value;

    private String unit;

    private String eventDate;
}
