package cps.middleware.exception;

import cps.middleware.models.response.Messages;

/**
 * @author RajAlok
 *
 * @param <T>
 *
 * @version V1.0
 */

public class CpsException extends RuntimeException {
    /**
     * Auto generated UID
     */
    private static final long serialVersionUID = 1L;

    public CpsException() {
        super();
    }

    public CpsException(String message) {
        super(message);
    }

    public CpsException(Messages messgage) {
        super(messgage.getMessageEntry().getText());
    }
}
