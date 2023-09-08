package com.unisys.trans.cps.middleware.controllers;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.MyCarrierRequest;
import com.unisys.trans.cps.middleware.models.response.BranchActivity;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.MyCarriersResponse;
import com.unisys.trans.cps.middleware.services.GetMyCarriersImpl;
import com.unisys.trans.cps.middleware.services.OperationalDashboardService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Raji
 * @version V1.0
 * @since 31-AUG-23
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "OpDashbaord Controller", protocols = "http")
public class OperationalDashboardController {

    
	private final OperationalDashboardService operationalDashboardService;
	private final GetMyCarriersImpl myCarriers;

	public OperationalDashboardController(OperationalDashboardService operationalDashboardService,GetMyCarriersImpl myCarriers) {
		this.operationalDashboardService = operationalDashboardService;
		this.myCarriers = myCarriers;
	}

	/**
	 * This method makes a rest call to FunctionAudit DB to get list of carriers and
	 * its booking count with percentage and send as response
	 * 
	 * @param branchId
	 * @return List<BranchActivity>
	 */

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/branchActivity", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<BranchActivity>> getBranchBookings(@RequestParam Integer branchId) {
        return new ResponseEntity<>(operationalDashboardService.getAllBranchBookings(branchId), true, null);
    }
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/getmycarriers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<MyCarriersResponse>> getMyCarriers(@Valid @RequestBody MyCarrierRequest request) {

        log.info("Get_My_Carriers Request Payload: {} ", request);

        ResponseEntity<List<MyCarriersResponse>> response = new ResponseEntity<>();

        try {

            List<MyCarriersResponse> carriersResponse = myCarriers.getMyCarriers(request);
            response.setResponse(carriersResponse);

        } catch (CpsException e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR001", "ERR", e.getMessage())));
            log.error(response.getErrorList().toString());
        } catch (Exception e) {
            response.setSuccessFlag(false);
            response.setErrorList(List.of(new MessageEntry("ERR002", "ERR", e.getMessage())));
            log.error(response.getErrorList().toString());
        }
        return response;
    }


}
