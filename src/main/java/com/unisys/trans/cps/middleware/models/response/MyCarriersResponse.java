package com.unisys.trans.cps.middleware.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCarriersResponse {

    private String carrier;

    private String status;
}
