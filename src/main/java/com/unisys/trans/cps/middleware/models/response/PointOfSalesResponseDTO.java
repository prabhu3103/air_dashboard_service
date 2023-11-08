package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import java.util.List;

@Data
public class PointOfSalesResponseDTO {

    private Long totalValue;

    private float momData;

    private float yoyData;

    List<POSResponseDTO> posList;
}
