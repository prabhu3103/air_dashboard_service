package com.unisys.trans.cps.middleware.models.response;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public interface FunctionAuditDB {
	    
	@Value("#{target.CARRIER}")
	    String getCarrier();

	    @Value("#{target.EVENTDATE}")
	    String getEventDate();

	    @Value("#{target.NUMBER_OF_BOOKINGS}")
	    String getNumberOfBookings();

}
