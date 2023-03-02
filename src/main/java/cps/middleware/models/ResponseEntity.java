package cps.middleware.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * @author RajAlok
 *
 * @param <T>
 * 
 * @version V1.0
 */
@Component
public class ResponseEntity<T> implements Serializable {

	/**
	 * Auto generated Unique Identifier(UID)
	 */
	private static final long serialVersionUID = 8334826799209861627L;

	/* T is the generic type for  all the model classes respective to Business */
	private T response;

	/* successFlag is for transaction completed successfully or not */
	private boolean successFlag;

	/* List of all the validation error based on business requirement */
	private List<String> errorList;

	public ResponseEntity() {
		super();
	}

	public ResponseEntity(T response, boolean successFlag, List<String> errorList) {
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

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
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
