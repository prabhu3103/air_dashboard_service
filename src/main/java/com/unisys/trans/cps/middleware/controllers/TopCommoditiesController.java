package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.constants.AuditAction;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TopCommoditiesResponseDTO;
import com.unisys.trans.cps.middleware.services.topCommoditiesService.TopCommodityService;
import com.unisys.trans.cps.middleware.utilities.CpsAuditUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class TopCommoditiesController {

    @Autowired
    private CpsAuditUtils auditUtils;

    @Autowired
    private TopCommodityService topCommodityService;


    @GetMapping(value = "/topcommodities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TopCommoditiesResponseDTO>> topCommodities(@Valid @RequestBody AirlineDashboardRequest airlineDashboardRequest) {
        log.info("Inside topCommodities method..");
        ResponseEntity<List<TopCommoditiesResponseDTO>> response = new ResponseEntity<>();
        log.info("Area by : " + airlineDashboardRequest.getAreaBy());

        try {
            List<TopCommoditiesResponseDTO> topCommoditiesResponse = topCommodityService.getTopCommodities(airlineDashboardRequest);
            response.setResponse(topCommoditiesResponse);
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
