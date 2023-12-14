package com.unisys.trans.cps.middleware.services.customerinquiriesservice;

import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.repository.CustomerInquiryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class CustomerInquiriesServiceImpl implements CustomerInquiriesService {

    private final CustomerInquiryRepository contactRepo;
    public CustomerInquiriesServiceImpl(CustomerInquiryRepository contactRepo){
        this.contactRepo = contactRepo;
    }


    /*Get carrier Count from contact query table
    fetch count from contact query table.
    @return count Integer.
     */
    @Override
    public int getInquiryCount(InquiryRequest request) {
        return contactRepo.getCount(request.getCarrier(), getPastDaysDate(request.getDate()));
    }


    /*Get all Queries from contact query table
      fetch queries for last 30 days
      return  ContactQuery list
     */

    @Override
    public List<ContactQuery> getAllContactQueries(InquiryRequest request) {
        return contactRepo.getContactQuery(request.getCarrier(), getPastDaysDate(request.getDate()));

    }


    //convert string date to LocalDateTime
  private LocalDateTime getPastDaysDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(date, formatter).minusDays(30).withHour(0).withMinute(0).withSecond(0);
        } catch (DateTimeParseException e) {
            return LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0);
        }

    }

}


