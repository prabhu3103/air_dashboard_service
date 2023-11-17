package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import java.util.List;

@Data
public class DomesticInternationalResponseDTO {

    private float totalValue;

    List<TopDomesticInternationalResponseDTO> topDomesticInternationalResponseDTOList;

}
