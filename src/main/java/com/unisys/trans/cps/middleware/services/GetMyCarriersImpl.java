package com.unisys.trans.cps.middleware.services;

import com.unisys.trans.cps.middleware.models.request.MyCarrierRequest;
import com.unisys.trans.cps.middleware.models.response.AirlineProfile;
import com.unisys.trans.cps.middleware.models.response.BranchAccount;
import com.unisys.trans.cps.middleware.models.response.MyCarriersResponse;
import com.unisys.trans.cps.middleware.repository.AirlineProfileRepository;
import com.unisys.trans.cps.middleware.repository.BranchAccountRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GetMyCarriersImpl implements GetMyCarriers {
	
	private final BranchAccountRepository branchAccountRepository;
	private final AirlineProfileRepository airlineProfileRepository;

	public GetMyCarriersImpl(BranchAccountRepository branchAccountRepository,AirlineProfileRepository airlineProfileRepository) {
		this.branchAccountRepository = branchAccountRepository;
		this.airlineProfileRepository = airlineProfileRepository;
	}

    @Override
    public List<MyCarriersResponse> getMyCarriers(MyCarrierRequest myCarrierRequest) {

        ArrayList<MyCarriersResponse> response = new ArrayList<MyCarriersResponse>();

        List<BranchAccount> branchAccounts = branchAccountRepository.findBranchAccountByBranchId(myCarrierRequest.getBranchId());
        List<AirlineProfile> airlineProfiles = airlineProfileRepository.getAllAirline();

        for (AirlineProfile airlineProfile : airlineProfiles) {
            int denied = 0;
            int approved = 0;
            int requested = 0;
            for (BranchAccount branchAccount : branchAccounts) {
                if (branchAccount.getCarrierCode().equalsIgnoreCase(airlineProfile.getCarrierCode())) {
                    if (branchAccount.getStatus() == 0) {
                        denied++;
                    } else if (branchAccount.getStatus() == 1) {
                        approved++;
                    } else {
                        requested++;
                    }
                }
            }
            if (approved > 0) {
                response.add(new MyCarriersResponse(airlineProfile.getCarrierCode(), "Approved"));
            } else if (requested > 0) {
                response.add(new MyCarriersResponse(airlineProfile.getCarrierCode(), "Requested"));
            } else if (denied > 0) {
                response.add(new MyCarriersResponse(airlineProfile.getCarrierCode(), "Denied"));
            } else {
                response.add(new MyCarriersResponse(airlineProfile.getCarrierCode(), "Available"));
            }
        }
        return response;
    }
}
