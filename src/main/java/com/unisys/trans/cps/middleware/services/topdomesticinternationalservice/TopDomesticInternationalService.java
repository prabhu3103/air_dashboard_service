package com.unisys.trans.cps.middleware.services.topdomesticinternationalservice;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.DomesticInternationalResponseDTO;

public interface TopDomesticInternationalService {
    DomesticInternationalResponseDTO getTopDomesticInternational(AirlineDashboardRequest airlineDashboardRequest);
}
