package com.unisys.trans.cps.middleware.services.branchrequestsservice;

import com.unisys.trans.cps.middleware.models.response.BranchRequestDTO;
import com.unisys.trans.cps.middleware.repository.BranchRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchRequestsServiceImpl implements BranchRequestsService {

    @Autowired
    BranchRequestsRepository branchRequestsRepo;

    /*Get count of Carriers in Branch Account
    fetch reject
    fetch approved
    fetch pending
     return reject, pending , approved
     */
    @Override
    public BranchRequestDTO getBranchRequests(String carrierCode) {
        BranchRequestDTO branchRequestDTO = new BranchRequestDTO();

        //get List of status from contact query
        List<String> status = branchRequestsRepo.getStatus(carrierCode);


        int reject = 0; // status 0
        int pending = 0; // status 2
        int approved = 0; // status 1
        if (status != null) {

            for (String str : status) {

                if (str.equals("0")) {
                    reject++;
                } else if (str.equals("1")) {
                    approved++;
                } else pending++;

            }
        }

        //Fetched values set  to DTO
        branchRequestDTO.setRejected(reject);
        branchRequestDTO.setApproved(approved);
        branchRequestDTO.setPending(pending);

        return branchRequestDTO;

    }
}

