package com.unisys.trans.cps.middleware.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.request.InquiryRequest;
import com.unisys.trans.cps.middleware.models.response.BranchRequestDTO;
import com.unisys.trans.cps.middleware.repository.BranchRequestsRepository;
import com.unisys.trans.cps.middleware.services.branchrequestsservice.BranchRequestsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BranchRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchRequestsRepository repository;

    @MockBean
    private BranchRequestsService service;

    private static InquiryRequest res = new InquiryRequest();

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBranchRequestCountTest() throws Exception {

        List<String> value = new ArrayList<>();
        value.add("1");
        value.add("2");
        value.add("0");

        res.setCarrier("AC");


        BranchRequestDTO dto = new BranchRequestDTO();
        dto.setRejected(1);
        dto.setApproved(1);
        dto.setPending(1);

        when(service.getBranchRequests(anyString())).thenReturn(dto);

        when(repository.getStatus("'AC'")).thenReturn(value);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/airline-dashboard/branchrequest-count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(res))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));

    }


    @Test
    void getBranchRequestCountCPSExceptionTest() throws CpsException {

        res.setCarrier("AC");

        when(service.getBranchRequests(anyString())).thenThrow(CpsException.class);
        when(repository.getStatus("'AC'")).thenThrow(CpsException.class);

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/v1/airline-dashboard/branchrequest-count")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(res))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            // .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
