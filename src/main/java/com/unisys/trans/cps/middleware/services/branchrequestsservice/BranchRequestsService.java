package com.unisys.trans.cps.middleware.services.branchrequestsservice;

import com.unisys.trans.cps.middleware.models.response.BranchRequestDTO;


public interface BranchRequestsService {

   BranchRequestDTO getBranchRequests(String carrierCode);
}
