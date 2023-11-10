package com.unisys.trans.cps.middleware.services.topproductservice;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopProductResponseDTO;

import java.util.List;

public interface TopProductsService {

    List<TopProductResponseDTO> getTopProducts(AirlineDashboardRequest airlineDashboardRequest);
}
