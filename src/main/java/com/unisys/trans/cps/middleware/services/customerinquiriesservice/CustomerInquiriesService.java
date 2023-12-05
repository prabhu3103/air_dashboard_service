package com.unisys.trans.cps.middleware.services.customerinquiriesservice;

import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;

import java.util.List;

public interface CustomerInquiriesService {

    int getInquiryCount(InquiryRequest request);

    List<ContactQuery> getAllContactQueries(InquiryRequest request);


}
