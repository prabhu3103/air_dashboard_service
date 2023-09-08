package com.unisys.trans.cps.middleware.services;

import java.util.List;

import com.unisys.trans.cps.middleware.models.request.MyCarrierRequest;
import com.unisys.trans.cps.middleware.models.response.MyCarriersResponse;

public interface GetMyCarriers {

    List<MyCarriersResponse> getMyCarriers(MyCarrierRequest myCarrierRequest);
}
