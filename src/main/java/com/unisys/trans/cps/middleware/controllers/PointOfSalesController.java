package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.services.pointOfSalesService.PointOfSalesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class PointOfSalesController {

    private final PointOfSalesService aPointOfSalesService;

    public PointOfSalesController( PointOfSalesService aPointOfSalesService) {
        this.aPointOfSalesService = aPointOfSalesService;
    }

    @GetMapping(value = "/pointofsales", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<PointOfSalesResponseDTO>> getPointOfSales(AirlineDashboardRequest airlineDashboardRequest) {

        log.info("getPointOfSales Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<PointOfSalesResponseDTO>> response = new ResponseEntity<>();

        try {
            List<PointOfSalesResponseDTO> pointOfSalesResponse = aPointOfSalesService.getPointOfSales(airlineDashboardRequest);
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
