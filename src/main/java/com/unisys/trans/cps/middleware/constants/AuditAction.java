package com.unisys.trans.cps.middleware.constants;

/**
 * @author RajAlok
 * @version V1.0
 */
public enum AuditAction {
    CREATE_AUDIT, UPDATE_AUDIT, GET_ALL_AUDIT;
    public String perform() {

        return switch (this) {
            case CREATE_AUDIT -> "save";
            case UPDATE_AUDIT -> "update";
            case GET_ALL_AUDIT -> "audits";
        };
    }
}