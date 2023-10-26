package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.constants.AuditAction;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.utilities.CpsAuditUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/v1")
public class TopLanesController {

    /*@Autowired
    private CpsAuditUtils auditUtils;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getDemo() {

        log.info("Inside getDemo method of DemoController");
        ResponseEntity<String> response = new ResponseEntity<>();
        response.setSuccessFlag(true);
        auditUtils.feedAudit(AuditAction.CREATE_AUDIT.perform(), response, null, Arrays.asList("086"));
        response.setResponse(new String("Demo Working Fine"));
        response.setErrorList(null);
        return response;
    }*/
}
