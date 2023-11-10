package com.unisys.trans.cps.middleware.services.pointofsalesservice;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;

import java.util.List;

public interface PointOfSalesService {

    List<PointOfSalesResponseDTO> getPointOfSales(AirlineDashboardRequest airlineDashboardRequest);
}
