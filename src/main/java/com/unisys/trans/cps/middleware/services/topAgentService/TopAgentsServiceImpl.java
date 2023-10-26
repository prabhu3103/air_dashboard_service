package com.unisys.trans.cps.middleware.services.topAgentService;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;
import com.unisys.trans.cps.middleware.repository.AuditRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopAgentsServiceImpl implements TopAgentsService{

    @Autowired
    AuditRequestRepository auditRequestRepository;

    @Override
    public List<TopAgentsResponseDTO> getTopAgents(AirlineDashboardRequest airlineDashboardRequest) {
        List<Object[]>topObjects=auditRequestRepository.getTopAgents();
        return null;
    }
}
