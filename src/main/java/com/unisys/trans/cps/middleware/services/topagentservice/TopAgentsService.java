package com.unisys.trans.cps.middleware.services.topagentservice;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.AgentResponseDTO;

public interface TopAgentsService {

    AgentResponseDTO getTopAccounts(AirlineDashboardRequest airlineDashboardRequest);
}
