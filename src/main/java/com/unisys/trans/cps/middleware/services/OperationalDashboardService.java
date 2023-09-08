package com.unisys.trans.cps.middleware.services;


import java.util.List;

import com.unisys.trans.cps.middleware.models.response.BranchActivity;


public interface OperationalDashboardService {

    List<BranchActivity> getAllBranchBookings(int branchId);

}
