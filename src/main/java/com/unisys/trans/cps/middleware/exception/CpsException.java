package com.unisys.trans.cps.middleware.exception;

import java.io.Serial;

/**
 * @author RajAlok
 * @version V1.0
 */

public class CpsException extends RuntimeException {
    /**
     * Auto generated UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public CpsException() {
        super();
    }

    public CpsException(String message) {
        super(message);
    }
}
