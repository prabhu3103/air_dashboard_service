package com.unisys.trans.cps.middleware.services.pointofsalesservice;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;

public interface PointOfSalesService {

    PointOfSalesResponseDTO getPointOfSales(AirlineDashboardRequest airlineDashboardRequest);
}
