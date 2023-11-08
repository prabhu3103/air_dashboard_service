package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class POSResponseDTO {
    
    private String valueType;

    private Long value;

    private String unit;

    private String eventDate;

    private float momData;

    private float yoyData;
}
