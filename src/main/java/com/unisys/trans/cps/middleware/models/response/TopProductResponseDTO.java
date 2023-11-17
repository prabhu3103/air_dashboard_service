package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

@Data
public class TopProductResponseDTO {

    private  String  productCode;

    private  String productDescription;

    private String valueType;

    private float value;
    private String unit;

    private float momData;

    private float yoyData;

}
