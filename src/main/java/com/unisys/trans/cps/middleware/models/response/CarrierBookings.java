package com.unisys.trans.cps.middleware.models.response;


import java.io.Serializable;


import lombok.Data;

	@Data
	public class CarrierBookings implements Serializable {

		private static final long serialVersionUID = 1L;
		private String carrier;
	    private String numberOfBookings;
	    private float percentage;
	    
		
	    public CarrierBookings(String carrier,  String numberOfBookings) {
		this.carrier = carrier;
		this.numberOfBookings = numberOfBookings;
		}
	}



