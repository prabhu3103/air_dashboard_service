package com.unisys.trans.cps.middleware.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisys.trans.cps.middleware.constants.SubFunction;
import com.unisys.trans.cps.middleware.models.request.AuditRequestDTO;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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

    private AuditRequestDTO getAuditRequestBuilder(ResponseEntity<?> responseEntity, Object payload, String carrier) throws JsonProcessingException, UnknownHostException {
        ObjectMapper mapper = new ObjectMapper();
        AuditRequestDTO auditRequestDTO = new AuditRequestDTO();
        auditRequestDTO.setAwbNumber(EMPTY_STRING);
        auditRequestDTO.setCarrier(carrier);
        auditRequestDTO.setConfNumber(EMPTY_STRING);
        auditRequestDTO.setDocument(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload));
        if (responseEntity.isSuccessFlag()) {
            auditRequestDTO.setErrorTxt(EMPTY_STRING);
            auditRequestDTO.setHostError(EMPTY_STRING);
            auditRequestDTO.setStatus(SUCCESS);
        } else {
            auditRequestDTO.setErrorTxt(responseEntity.getErrorList().get(0).getText());
            auditRequestDTO.setHostError(responseEntity.getErrorList().get(0).getText());
            auditRequestDTO.setStatus(FAIL);
        }
        auditRequestDTO.setEventDate(LocalDateTime.now());
        auditRequestDTO.setFromPage(FROM_PAGE);
        auditRequestDTO.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        auditRequestDTO.setPortalFunction(PORTAL_FUNCTION);
        auditRequestDTO.setPortalIdentity(PORTAL_IDENTITY);
        auditRequestDTO.setServerName(SERVER_NAME);
        auditRequestDTO.setSubFunction(String.valueOf(SubFunction.INQUIRY));
        auditRequestDTO.setTxnStatus(EMPTY_STRING);
        /* Need to change once the login module is ready to use */
        auditRequestDTO.setUserId("BiswalT");
        auditRequestDTO.setBranchId(10010);

        log.info("Audit_Request_JSON: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(auditRequestDTO));
        return auditRequestDTO;
    }
}