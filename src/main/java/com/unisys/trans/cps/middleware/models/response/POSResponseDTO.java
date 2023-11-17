package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class POSResponseDTO {
    
    private String valueType;

    private float value;

    private String unit;

    private String eventDate;

}
