package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.models.response.BranchRequestDTO;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.services.branchrequestsservice.BranchRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/airline-dashboard")
public class BranchRequestsController {


    private final BranchRequestsService branchRequests;

    public BranchRequestsController(BranchRequestsService branchRequests) {
        this.branchRequests = branchRequests;
    }

    //To get total count of particluar carrier in Branch Account table
    /* Get all  branchrequest count.
    request -> carrier
    fetch  from branch request table
    @return inquiry count in BranchRequestDTO
    */
    @PostMapping(value = "/branchrequest-count", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<BranchRequestDTO> getBranchRequests(@AuthenticationPrincipal Jwt principal, @RequestBody InquiryRequest request) {

        log.info("Airline-dashboard-BranchRequests Request: {} ", request);

        ResponseEntity<BranchRequestDTO> response = new ResponseEntity<>();

        try {
            BranchRequestDTO getBranchRequests = branchRequests.getBranchRequests(request.getCarrier());
            response.setResponse(getBranchRequests);
            response.setSuccessFlag(true);

        } catch (CpsException e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR001", "ERR", e.getMessage())));
        } catch (Exception e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR002", "ERR", e.getMessage())));
        }

        return response;
    }

}
