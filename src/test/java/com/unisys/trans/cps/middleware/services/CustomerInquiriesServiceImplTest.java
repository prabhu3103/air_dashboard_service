package com.unisys.trans.cps.middleware.services;


import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.repository.CustomerInquiryRepository;
import com.unisys.trans.cps.middleware.services.customerinquiriesservice.CustomerInquiriesService;
import com.unisys.trans.cps.middleware.services.customerinquiriesservice.CustomerInquiriesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;

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

    @Mock
    private CustomerInquiriesService service;

    @Test
    void getCustomerInquiriesTest(){
        when(repository.getCount(anyString(), any(LocalDateTime.class))).thenReturn(0);
        int response = serviceImpl.getInquiryCount(InquiryRequest.builder().date("2023-11-10 11:11:11").carrier("AC").build());
        assertNotNull(response);
    }

}
