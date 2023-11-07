package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.constants.AuditAction;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TopProductResponseDTO;
import com.unisys.trans.cps.middleware.services.pointOfSalesService.PointOfSalesService;
import com.unisys.trans.cps.middleware.services.topProductService.TopProductsService;
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
public class TopProductsController {

    private TopProductsService topProductService;

    public TopProductsController( TopProductsService topProductService) {
        this.topProductService = topProductService;
    }

    @GetMapping(value = "/topproducts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<TopProductResponseDTO>> getTopProducts(AirlineDashboardRequest airlineDashboardRequest) {

        log.info("getTopProducts Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<List<TopProductResponseDTO>> response = new ResponseEntity<>();

        try {
            List<TopProductResponseDTO> topProductResponse = topProductService.getTopProducts(airlineDashboardRequest);
            response.setResponse(topProductResponse);
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
