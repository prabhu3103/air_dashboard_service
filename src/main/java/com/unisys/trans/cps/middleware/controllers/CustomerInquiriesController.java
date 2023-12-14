package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.models.response.CustomerInquiryDTO;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.services.customerinquiriesservice.CustomerInquiriesService;
import com.unisys.trans.cps.middleware.utilities.ExcelExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/airline-dashboard")
public class CustomerInquiriesController {

    private final CustomerInquiriesService customerInquiriesService;

    public CustomerInquiriesController(CustomerInquiriesService customerInquiriesService) {
        this.customerInquiriesService = customerInquiriesService;
    }


    /* Get customer inquiry count for last 30 days.
    request -> date and carrier
    fetch  from contact query table
    @return inquiry count in  CustomerInquiryDTO
    */
    @PostMapping(value = "/customer-inquiry-count", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CustomerInquiryDTO> getCustomerInquiryCount(@AuthenticationPrincipal Jwt principal, @RequestBody InquiryRequest request) {

        log.info("Airline Strategic Dashboard inquiries  Request Payload: {} ", request);

        ResponseEntity<CustomerInquiryDTO> response = new ResponseEntity<>();
        CustomerInquiryDTO customerInquiryDTO = new CustomerInquiryDTO();

        try {
            int inquiry = customerInquiriesService.getInquiryCount(request);

            customerInquiryDTO.setInquiryCount(inquiry);

            response.setResponse(customerInquiryDTO);
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


    @CrossOrigin(exposedHeaders = "Content-Disposition")
    @PostMapping("/customer-inquiry-export")
    org.springframework.http.ResponseEntity<Resource> customerInquiriesExportToExcel(@AuthenticationPrincipal Jwt principal, @RequestBody InquiryRequest request) throws IOException {

        log.info("Export inquiry Request= {} ", request);
        //filename
        String fileName = "attachment; filename=" + request.getCarrier() + "_Customer_Inquiries_" + request.getDate() + ".xlsx";

        //Service call
        List<ContactQuery> listUsers = customerInquiriesService.getAllContactQueries(request);
      //  log.info("Export_listUsers {} ", listUsers);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, fileName);

        // Intialize excelExporter class
        ExcelExporter excelExporter = new ExcelExporter(listUsers);

        //Excel sheet export call
        byte[] file = excelExporter.export();

        return org.springframework.http.ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayResource(file));

    }

}


