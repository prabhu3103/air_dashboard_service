package com.unisys.trans.cps.middleware.models.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopDomesticInternationalResponseDTO {

    private boolean intl;

    private String valueType;

    private Long value;

    private String unit;

}
