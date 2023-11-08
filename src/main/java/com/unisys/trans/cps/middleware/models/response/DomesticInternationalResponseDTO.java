package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import java.util.List;

@Data
public class DomesticInternationalResponseDTO {

    private Long totalValue;

    List<TopDomesticInternationalResponseDTO> topDomesticInternationalResponseDTOList;

}
