package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.DomesticInternationalResponseDTO;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TopDomesticInternationalResponseDTO;
import com.unisys.trans.cps.middleware.services.topDomesticInternationalService.TopDomesticInternationalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class DomesticInternationalController {

    private TopDomesticInternationalService topDomesticInternationalService;

    public DomesticInternationalController( TopDomesticInternationalService topDomesticInternationalService) {
        this.topDomesticInternationalService = topDomesticInternationalService;
    }

    @GetMapping(value = "/domesticinternational", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<DomesticInternationalResponseDTO> getTopDomesticInternationalAgents(AirlineDashboardRequest airlineDashboardRequest) {

        log.info("Airline Strategic Dashboard Request Payload: {} ", airlineDashboardRequest);

        ResponseEntity<DomesticInternationalResponseDTO> response = new ResponseEntity<>();

        try {
            DomesticInternationalResponseDTO responseDTO = topDomesticInternationalService.getTopDomesticInternational(airlineDashboardRequest);
            response.setResponse(responseDTO);
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
