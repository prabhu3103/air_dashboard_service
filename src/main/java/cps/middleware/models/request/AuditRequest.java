package cps.middleware.models.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author RajAlok
 * @version V1.0
 */
@Data
@Component
public class AuditRequest implements Serializable {
    /*Auto generated UID*/
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 12, message = "Invalid portalFunction")
    private String portalFunction;
    @Size(max = 20, message = "Invalid subFunction")
    private String subFunction;
    @Size(max = 32, message = "Invalid userId")
    private String userId;

    private long branchId;
    @NotNull
    @Size(max = 5, message = "Invalid carrier")
    private String carrier;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    @Size(max = 1, message = "Invalid txnStatus")
    private String txnStatus;
    @Size(max = 1, message = "Invalid status")
    private String status;
    private String document;
    @Size(max = 12, message = "Invalid awbNumber")
    private String awbNumber;
    @Size(max = 10, message = "Invalid confNumber")
    private String confNumber;
    @Size(max = 8, message = "Invalid serverName")
    private String serverName;
    @Size(max = 1000, message = "Invalid errorTxt")
    private String errorTxt;
    @Size(max = 50, message = "Invalid ipAddress")
    private String ipAddress;
    @NotNull
    @Size(max = 100)
    @NotNull(message = "Invalid portalIdentity")
    private String portalIdentity;
    @Size(max = 1000, message = "Invalid hostError")
    private String hostError;
    @Size(max = 20, message = "Invalid fromPage")
    private String fromPage;
}