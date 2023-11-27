package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TopProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdvanceFunctionAuditRepository advanceFunctionAuditRepository;
    @MockBean
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;
    @Test
    void getTopProductBookingAirportTest() throws Exception {

        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopProductsBookingAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","airport").
                        queryParam("filterValue","YYZ").
                        queryParam("carrier","AC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductBookingCountryTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Object[] mockObjectCurrentMonth = {10};
        Mockito.when(advanceFunctionAuditRepository.getTopProductsBookingCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","country").
                        queryParam("filterValue","IN").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductBookingContinentTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopProductsBookingContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","continent").
                        queryParam("filterValue","AP").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductBookingRegionTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopProductsBookingRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","region").
                        queryParam("filterValue","APAC").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductVolumeAirportTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsVolumeAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","volume").
                        queryParam("areaBy","airport").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductVolumeCountryTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsVolumeCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","volume").
                        queryParam("areaBy","country").
                        queryParam("filterValue","IN").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductVolumeContinentTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",20,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsVolumeContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","volume").
                        queryParam("areaBy","continent").
                        queryParam("filterValue","AP").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductVolumeRegionTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",233,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsVolumeRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","volume").
                        queryParam("areaBy","region").
                        queryParam("filterValue","APAC").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductWeightAirportTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",233,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","weight").
                        queryParam("areaBy","airport").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductWeightCountryTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",233,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsWeightCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","weight").
                        queryParam("areaBy","country").
                        queryParam("filterValue","IN").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductWeightContinentTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",233,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsWeightContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","weight").
                        queryParam("areaBy","continent").
                        queryParam("filterValue","AP").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductsWeightRegionTest() throws Exception {
        Object[] mockObject = {"GEN","AIRCRAFT TOOLING NON HAZ",233,1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopProductsWeightRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","weight").
                        queryParam("areaBy","region").
                        queryParam("filterValue","APAC").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopProductCPSExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopProductsBookingAirport(any(), any(), any(), any()))
                .thenThrow(new CpsException("Mocked exception message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate", "2023-01-01")
                        .queryParam("typeOfInfo", "bookingcount")
                        .queryParam("areaBy", "airport")
                        .queryParam("filterValue", "BLR")
                        .queryParam("carrier", "AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(false));
    }
    @Test
    void getTopProductExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopProductsBookingAirport(any(), any(), any(), any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-products")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate", "2023-01-01")
                        .queryParam("typeOfInfo", "bookingcount")
                        .queryParam("areaBy", "airport")
                        .queryParam("filterValue", "BLR")
                        .queryParam("carrier", "AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
    }
}

