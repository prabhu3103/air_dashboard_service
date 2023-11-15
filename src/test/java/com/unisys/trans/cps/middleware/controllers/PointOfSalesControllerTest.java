package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PointOfSalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PointOfSalesController aPointOfSalesController;

    @MockBean
    private AdvanceFunctionAuditRepository aAdvanceFunctionAuditRepository;

    @MockBean
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;

    @Test
    void getPointOfSalesTest() throws Exception {
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/point-of-sales?startDate=2023-06-08&endDate=2023-11-08&typeOfInfo=volume&areaBy=airport&filterValue=YYZ&carrier=AC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getPointOfSalesCPSExceptionTest() throws Exception{
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenThrow(new CpsException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/point-of-sales?startDate=2023-06-08&endDate=2023-11-08&typeOfInfo=volume&areaBy=airport&filterValue=YYZ&carrier=AC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(false));
    }


    List<Object[]> getPointOfSalesMock(){
        List<Object[]> mockedPosList = new ArrayList<>();

        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 9, 15, 10, 30), 100L});
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 10, 28, 14, 45), 200L});
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 11, 10, 9, 0), 150L});

        return mockedPosList;
    }

}