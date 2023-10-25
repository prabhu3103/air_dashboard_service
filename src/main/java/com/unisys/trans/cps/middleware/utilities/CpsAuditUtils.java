package com.unisys.trans.cps.middleware.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisys.trans.cps.middleware.constants.SubFunction;
import com.unisys.trans.cps.middleware.models.request.AuditRequest;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author RajAlok
 */
@Slf4j
@Component
public class CpsAuditUtils {


    private final WebClient webClient;
    private static final String PORTAL_IDENTITY = "CPS_NewGen";
    private static final String FROM_PAGE = "MARKET_PLACE";
    private static final String PORTAL_FUNCTION = "RATEESTIMATE";
    private static final String SERVER_NAME = "HSEAN230";
    private static final String EMPTY_STRING = "";
    private static final String SUCCESS = "S";
    private static final String FAIL = "F";

    @Autowired
    public CpsAuditUtils(WebClient webClient) {
        this.webClient = webClient;
    }

    public void feedAudit(@NotNull String operation, ResponseEntity<?> responseEntity, Object payload, @NotNull List<String> carriers) {
        for (String carrier : carriers) {
            try {
                webClient.post()
                        .uri("http://localhost:8081/cps/v1/audit/" + operation)
                        .headers(
                                httpHeaders ->
                                        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        )
                        .bodyValue(getAuditRequestBuilder(responseEntity, payload, carrier))
                        .retrieve()
                        .bodyToMono(Void.class)
                        .onErrorResume(throwable -> myFallBackMethod())
                        .blockOptional();
            } catch (CpsException | JsonProcessingException | UnknownHostException e) {
                throw new CpsException(String.valueOf(e));
            }
        }
    }

    private Mono<? extends Void> myFallBackMethod() {
        return Mono.empty();
    }

    private AuditRequest getAuditRequestBuilder(ResponseEntity<?> responseEntity, Object payload, String carrier) throws JsonProcessingException, UnknownHostException {
        ObjectMapper mapper = new ObjectMapper();
        AuditRequest auditRequest = new AuditRequest();
        auditRequest.setAwbNumber(EMPTY_STRING);
        auditRequest.setCarrier(carrier);
        auditRequest.setConfNumber(EMPTY_STRING);
        auditRequest.setDocument(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload));
        if (responseEntity.isSuccessFlag()) {
            auditRequest.setErrorTxt(EMPTY_STRING);
            auditRequest.setHostError(EMPTY_STRING);
            auditRequest.setStatus(SUCCESS);
        } else {
            auditRequest.setErrorTxt(responseEntity.getErrorList().get(0).getText());
            auditRequest.setHostError(responseEntity.getErrorList().get(0).getText());
            auditRequest.setStatus(FAIL);
        }
        auditRequest.setEventDate(LocalDateTime.now());
        auditRequest.setFromPage(FROM_PAGE);
        auditRequest.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        auditRequest.setPortalFunction(PORTAL_FUNCTION);
        auditRequest.setPortalIdentity(PORTAL_IDENTITY);
        auditRequest.setServerName(SERVER_NAME);
        auditRequest.setSubFunction(String.valueOf(SubFunction.INQUIRY));
        auditRequest.setTxnStatus(EMPTY_STRING);
        /* TODO : Need to change once the login module is ready to use */
        auditRequest.setUserId("BiswalT");
        auditRequest.setBranchId(10010);

        log.info("Audit_Request_JSON: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(auditRequest));
        return auditRequest;
    }
}