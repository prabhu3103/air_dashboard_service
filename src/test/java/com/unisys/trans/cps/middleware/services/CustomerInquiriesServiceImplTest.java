package com.unisys.trans.cps.middleware.services;


import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.repository.CustomerInquiryRepository;
import com.unisys.trans.cps.middleware.services.customerinquiriesservice.CustomerInquiriesService;
import com.unisys.trans.cps.middleware.services.customerinquiriesservice.CustomerInquiriesServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerInquiriesServiceImplTest {

    @InjectMocks
    private CustomerInquiriesServiceImpl serviceImpl;

    @Mock
    private CustomerInquiryRepository repository;

    @Test
    void getCustomerInquiriesCountTest(){
        InquiryRequest res =  new InquiryRequest();

        res.setDate("2023-11-10 11:11:11");
        res.setCarrier("AC");

        when(repository.getCount(anyString(), any(LocalDateTime.class))).thenReturn(1);
        int response = serviceImpl.getInquiryCount(res);

        Assertions.assertEquals(response, 1);

    }

    @Test
    void getCustomerInquiriesListTest(){
        InquiryRequest res =  new InquiryRequest();

        res.setDate("2023-11-10 11:11:11");
        res.setCarrier("AC");

        LocalDateTime dateTime = LocalDateTime.now();

        ContactQuery qry = new ContactQuery();
        qry.setEmail("test@gmail.com");
        qry.setName("testName");
        qry.setPhone("98876610");
        qry.setCarrier("AC");
        qry.setDate(dateTime);
        qry.setFunctionDesc("FunctionDesc");
        qry.setProblemDesc("ProblemDesc");

        List<ContactQuery> queries = new ArrayList<>();
        queries.add(qry);
        when(repository.getContactQuery(anyString(), any(LocalDateTime.class))).thenReturn(queries);
        List<ContactQuery> response = serviceImpl.getAllContactQueries(res);

         assertNotNull(response);
        Assertions.assertEquals(response.get(0).getCarrier(), "AC");
        Assertions.assertEquals(response.get(0).getName(), "testName");
    }


}
