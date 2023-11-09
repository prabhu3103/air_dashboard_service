package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TopProductResponseDTO;
import com.unisys.trans.cps.middleware.services.topProductService.TopProductsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/airline-dashboard")
public class TopProductsController {

    private TopProductsService topProductService;

    public TopProductsController( TopProductsService topProductService) {
        this.topProductService = topProductService;
    }

    @GetMapping(value = "/top-products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<TopProductResponseDTO>> getTopProducts(
            @AuthenticationPrincipal Jwt principal,
            @Valid AirlineDashboardRequest airlineDashboardRequest) {

        log.info("getTopProducts Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<TopProductResponseDTO>> response = new ResponseEntity<>();

        try {
            List<TopProductResponseDTO> topProductResponse = topProductService.getTopProducts(airlineDashboardRequest);
            response.setResponse(topProductResponse);
            response.setSuccessFlag(true);

        } catch (CpsException e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR011", "ERR", e.getMessage())));
        } catch (Exception e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR012", "ERR", e.getMessage())));
        }
        return response;
    }



}
