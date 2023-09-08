package cps.middleware.models.response;

import java.util.Objects;

import org.springframework.stereotype.Component;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Component
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Messages")
public class Messages  {

    @XmlElement(name = "MessageEntry", type = MessageEntry.class)
    private MessageEntry messageEntry;

    public Messages() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Messages(MessageEntry messageEntry) {
        super();
        this.messageEntry = messageEntry;
    }

    public MessageEntry getMessageEntry() {
        return messageEntry;
    }

    public void setMessageEntry(MessageEntry messageEntry) {
        this.messageEntry = messageEntry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageEntry);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Messages other = (Messages) obj;
        return Objects.equals(messageEntry, other.messageEntry);
    }

    @Override
    public String toString() {
        return "Messages [messageEntry=" + messageEntry + "]";
    }


}
