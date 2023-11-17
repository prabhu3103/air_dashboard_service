package com.unisys.trans.cps.middleware.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopAgentsResponseDTO {

    private String accNo;

    private String valueType;

    private float value;

    private String unit;

    private float momData;

    private float yoyData;
}
