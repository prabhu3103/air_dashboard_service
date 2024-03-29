package com.unisys.trans.cps.middleware.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.repository.CustomerInquiryRepository;
import com.unisys.trans.cps.middleware.services.customerinquiriesservice.CustomerInquiriesService;
import com.unisys.trans.cps.middleware.utilities.ExcelExporter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CustomerInquiriesControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerInquiryRepository repository;

    @MockBean
    private CustomerInquiriesService service;

    @Autowired
    ObjectMapper objectMapper;

    private   InquiryRequest res =new InquiryRequest();

    @Test
    void getCustomerInquiryCountTest() throws Exception {

        InquiryRequest res =new InquiryRequest();

        res.setCarrier("AC");
        res.setDate("2023-11-23 11:19:19");

        when(service.getInquiryCount(res)).thenReturn(1);
        when(repository.getCount(anyString(), any(LocalDateTime.class))).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/airline-dashboard/customer-inquiry-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(res))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));

    }

    @Test
    void getCustomerInquiryCountExceptionTest() throws Exception {


        InquiryRequest res =new InquiryRequest();

        res.setCarrier("AC");
        res.setDate("2023-11-23 11:19:19");

        when(service.getInquiryCount(res)).thenReturn(1);

        when(repository.getCount(anyString(), any(LocalDateTime.class))).thenThrow(CpsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/airline-dashboard/customer-inquiry-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(res))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));

    }

    @Test
    void ExcelExportTest() throws Exception {

        InquiryRequest res =new InquiryRequest();

        res.setCarrier("AC");
        res.setDate("2023-11-23 11:19:19");

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

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "fileName");

        when(service.getAllContactQueries(res)).thenReturn(queries);

        when(repository.getContactQuery(anyString(), any(LocalDateTime.class))).thenReturn(queries);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/airline-dashboard/customer-inquiry-export")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(res))
                        .headers(header)
                        .accept("application/vnd.ms-excel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/vnd.ms-excel"));

        //                    .andExpect(MockMvcResultMatchers.jsonPath("$.response.emailAddress").value("Anita.Kumari@in.unisys.com"));




    }
}
