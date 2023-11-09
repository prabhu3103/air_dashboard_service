package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TopLanesResponseDTO;
import com.unisys.trans.cps.middleware.services.topLanesService.TopLanesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/airline-dashboard")
public class TopLanesController {

    private TopLanesService topLanesService;
    public TopLanesController( TopLanesService topLanesService) {
        this.topLanesService = topLanesService;
    }
    @GetMapping(value = "/top-lanes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<TopLanesResponseDTO>> getTopLanes(
            @AuthenticationPrincipal Jwt principal,
            @Valid AirlineDashboardRequest airlineDashboardRequest) {

        log.info("getTopLanes Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<TopLanesResponseDTO>> response = new ResponseEntity<>();

        try {
            List<TopLanesResponseDTO> topLanesResponse = topLanesService.getTopLanes(airlineDashboardRequest);
            response.setResponse(topLanesResponse);
            response.setSuccessFlag(true);

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
