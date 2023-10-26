package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.constants.AuditAction;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;
import com.unisys.trans.cps.middleware.services.topAgentService.TopAgentsServiceImpl;
import com.unisys.trans.cps.middleware.utilities.CpsAuditUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class TopAgentsController {

    @Autowired
    private CpsAuditUtils auditUtils;

    @Autowired
    private TopAgentsServiceImpl topAgentsService;

    @PostMapping(value = "/topagents", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<TopAgentsResponseDTO>> getTopAgents(AirlineDashboardRequest airlineDashboardRequest) {

        log.info("inviteCoworkers Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<TopAgentsResponseDTO>> response = new ResponseEntity<>();

        try {
            List<TopAgentsResponseDTO> responseABC = topAgentsService.getTopAgents(airlineDashboardRequest);

        } catch (CpsException e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR009", "ERR", e.getMessage())));
        } catch (Exception e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR010", "ERR", e.getMessage())));
        }
        return response;
    }
}
