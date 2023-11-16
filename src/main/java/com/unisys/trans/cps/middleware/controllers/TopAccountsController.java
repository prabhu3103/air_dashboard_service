package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.AgentResponseDTO;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.services.topagentservice.TopAgentsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/airline-dashboard")
public class TopAccountsController {

    private TopAgentsService topAgentsService;
    public TopAccountsController(TopAgentsService topAgentsService) {
        this.topAgentsService = topAgentsService;
    }

    @GetMapping(value = "/top-accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<AgentResponseDTO>> getTopAccounts(
            @AuthenticationPrincipal Jwt principal,
            @Valid AirlineDashboardRequest airlineDashboardRequest) {

        log.info("Airline Strategic Dashboard Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<AgentResponseDTO>> response = new ResponseEntity<>();
        List<AgentResponseDTO> agentResponseDTOS = new ArrayList<>();

        try {
            AgentResponseDTO responseDTO = topAgentsService.getTopAccounts(airlineDashboardRequest);
            if(responseDTO.getNewAccount()!=0){
                agentResponseDTOS.add(responseDTO);
            }
            response.setResponse(agentResponseDTOS);
            response.setSuccessFlag(true);

        } catch (CpsException e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR005", "ERR", e.getMessage())));
        } catch (Exception e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR006", "ERR", e.getMessage())));
        }
        return response;
    }
}
