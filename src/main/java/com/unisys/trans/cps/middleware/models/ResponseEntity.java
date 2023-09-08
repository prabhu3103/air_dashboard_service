package com.unisys.trans.cps.middleware.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @param <T>
 * @author RajAlok
 * @version V1.0
 */

@Component
@ApiModel(value = "ResponseEntity", description = "Wrapper Entity for all the Response")
public class ResponseEntity<T> implements Serializable {

    /**
     * Auto generated Unique Identifier(UID)
     */
    @Serial
    private static final long serialVersionUID = 8334826799209861627L;

    /* successFlag is for transaction completed successfully or not */
    @ApiModelProperty(notes = "Success Flag.", required = true, position = 0)
    private boolean successFlag;

    /* T is the generic type for  all the model classes respective to Business */
    @ApiModelProperty(notes = "Response object.", required = false, position = 1)
    private T response;

    /* List of all the validation error based on business requirement */
    @ApiModelProperty(notes = "Error List.", required = false, position = 2)
    private List<?> errorList;

    public ResponseEntity() {
        super();
    }

    public ResponseEntity(T response, boolean successFlag, List<?> errorList) {
        super();
        this.response = response;
        this.successFlag = successFlag;
        this.errorList = errorList;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
    }

    public List<?> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<?> errorList) {
        this.errorList = errorList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorList, response, successFlag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResponseEntity<?> other = (ResponseEntity<?>) obj;
        return Objects.equals(errorList, other.errorList) && Objects.equals(response, other.response)
                && successFlag == other.successFlag;
    }

    @Override
    public String toString() {
        return "BaseModel [response=" + response + ", successFlag=" + successFlag + ", errorList=" + errorList + "]";
    }


}
