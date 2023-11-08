package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class TopProductResponseDTO {

    private  String  ProductCode;

    private  String productDescription;

    private String valueType;

    private Long value;
    private String unit;

    private float momData;

    private float yoyData;

}
