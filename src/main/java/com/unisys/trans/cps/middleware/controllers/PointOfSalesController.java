package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.services.pointOfSalesService.PointOfSalesServiceImpl;
import com.unisys.trans.cps.middleware.utilities.CpsAuditUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/v1")
public class PointOfSalesController {


    @Autowired
    private CpsAuditUtils auditUtils;

    @Autowired
    private PointOfSalesServiceImpl aPointOfSalesServiceImpl;

    @PostMapping(value = "/pointofsales", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<PointOfSalesResponseDTO>> getPointOfSales(@Valid @RequestBody AirlineDashboardRequest airlineDashboardRequest) {

        log.info("getTopAgents Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<PointOfSalesResponseDTO>> response = new ResponseEntity<>();

        try {
            List<PointOfSalesResponseDTO> pointOfSalesResponse = aPointOfSalesServiceImpl.getPointOfSales(airlineDashboardRequest);
            response.setResponse(pointOfSalesResponse);
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
