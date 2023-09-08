package com.unisys.trans.cps.middleware.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.xml.bind.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ERROR", propOrder = {
		"code",
		"messageType",
		"msg"
})
public class ErrorType {


	protected String code;

	protected String messageType;
	@XmlElement(name = "MSG")
	protected String msg;

	public ErrorType(){
		super();}
	public ErrorType(String code, String messageType, String text) {
		super();
		this.code = code;
		this.messageType = messageType;
		this.msg = text;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * Gets the value of the msg property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMSG() {
		return msg;
	}

	/**
	 * Sets the value of the msg property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setMSG(String value) {
		this.msg = value;
	}

}
