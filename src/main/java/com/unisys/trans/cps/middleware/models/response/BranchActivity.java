package com.unisys.trans.cps.middleware.models.response;


import java.io.Serializable;
import java.util.List;

import lombok.Data;

	@Data
	public class BranchActivity implements Serializable {

		private String eventDate;
		private String totalBooking;
	    private List<CarrierBookings> carrierWiseBookings;
	    
			}



