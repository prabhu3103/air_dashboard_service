package com.unisys.trans.cps.middleware.services.topDomesticInternationalService;

import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.DomesticInternationalResponseDTO;
import com.unisys.trans.cps.middleware.models.response.TopDomesticInternationalResponseDTO;

import java.util.List;

public interface TopDomesticInternationalService {
    DomesticInternationalResponseDTO getTopDomesticInternational(AirlineDashboardRequest airlineDashboardRequest);
}
