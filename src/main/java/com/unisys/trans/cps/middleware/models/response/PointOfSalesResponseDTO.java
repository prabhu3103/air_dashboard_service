package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import java.util.List;

@Data
public class PointOfSalesResponseDTO {

    private Long totalValue;

    List<POSResponseDTO> posList;
}
