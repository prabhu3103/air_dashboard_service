package cps.middleware.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cps.middleware.models.ResponseEntity;

@RestController
@RequestMapping("/v1")
@Api(value = "Demo Controller", protocols = "http")
public class DemoController {

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Demo API", response = ResponseEntity.class)
	@ResponseBody ResponseEntity getDemo() {
		
		ResponseEntity<String> response = new ResponseEntity<String>();
		response.setSuccessFlag(true);
		response.setResponse(new String("Demo Working Fine"));
		response.setErrorList(null);
		return response;
	}
}
