package com.unisys.trans.cps.middleware.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.services.pointofsalesservice.PointOfSalesServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PointOfSalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PointOfSalesController aPointOfSalesController;

    @Mock
    private PointOfSalesServiceImpl aPointOfSalesServiceImpl;

    @Test
    void getPointOfSalesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/point-of-sales?startDate=2023-06-08&endDate=2023-11-08&typeOfInfo=volume&areaBy=airport&filterValue=YYZ&carrier=AC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getPointOfSalesExceptionTest() throws Exception {
        AirlineDashboardRequest aAirlineDashboardRequest = AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").filterValue("YYZ").carrier("AC").build();
        ResponseEntity<List<PointOfSalesResponseDTO>> response = aPointOfSalesController.getPointOfSales(null,null);
        assertTrue(response.getResponse().size() == 0);
    }

    @Test
    void getPointOfSalesCPSExceptionTest() throws Exception {
        AirlineDashboardRequest aAirlineDashboardRequest = AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("XXX").filterValue("YYZ").carrier("AC").build();
        when(aPointOfSalesServiceImpl.getPointOfSales(aAirlineDashboardRequest)).thenThrow(new CpsException("Mocked CpsException"));
        ResponseEntity<List<PointOfSalesResponseDTO>> response = aPointOfSalesController.getPointOfSales(null,aAirlineDashboardRequest);
        assertFalse(response.isSuccessFlag());

    }


    List<Object[]> getPointOfSalesMock(){
        List<Object[]> mockedPosList = new ArrayList<>();

        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 9, 15, 10, 30), 100L});
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 10, 28, 14, 45), 200L});
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 11, 10, 9, 0), 150L});

        return mockedPosList;
    }

}