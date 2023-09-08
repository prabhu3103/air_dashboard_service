package com.unisys.trans.cps.middleware.models.response;

import jakarta.xml.bind.annotation.*;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MessageEntry")

public class MessageEntry {
	
	@XmlAttribute(name = "code")
	private String code;
	
	@XmlAttribute(name = "messageType")
	private String messageType;
	
	@XmlElement(name = "Text", type = String.class)
	private String text;

	public MessageEntry() {
		super();
	}

	public MessageEntry(String code, String messageType, String text) {
		super();
		this.code = code;
		this.messageType = messageType;
		this.text = text;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, messageType, text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageEntry other = (MessageEntry) obj;
		return Objects.equals(code, other.code) && Objects.equals(messageType, other.messageType)
				&& Objects.equals(text, other.text);
	}

	@Override
	public String toString() {
		return "MessageEntry [code=" + code + ", messageType=" + messageType + ", text=" + text + "]";
	}

	
}
