package com.unisys.trans.cps.middleware.services.topcommoditiesservice;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopCommoditiesResponseDTO;

import java.util.List;

public interface TopCommodityService {

    List<TopCommoditiesResponseDTO> getTopCommodities(AirlineDashboardRequest airlineDashboardRequest);
}
