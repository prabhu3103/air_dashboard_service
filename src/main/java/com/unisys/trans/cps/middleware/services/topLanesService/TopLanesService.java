package com.unisys.trans.cps.middleware.services.topLanesService;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopLanesResponseDTO;

import java.util.List;

public interface TopLanesService {

    List<TopLanesResponseDTO> getTopLanes(AirlineDashboardRequest airlineDashboardRequest);
}
