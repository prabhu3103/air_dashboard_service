package com.unisys.trans.cps.middleware.services.topAgentService;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;

import java.util.List;

public interface TopAgentsService {

    List<TopAgentsResponseDTO> getTopAccounts(AirlineDashboardRequest airlineDashboardRequest);
}
