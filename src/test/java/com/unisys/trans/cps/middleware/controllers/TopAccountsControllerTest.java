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
class TopAccountsControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @MockBean
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;

    @Test
    void getTopAccountsBookingAirportTest() throws Exception {

        Object[] mockObject = {"BOM0677I", "BookingCount", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsBookingAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","airport").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }
    @Test
    void getTopAccountsBookingCountryTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "BookingCount", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsBookingCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsBookingContinentTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "BookingCount", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsBookingContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsBookingRegionTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "BookingCount", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsBookingRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsVolumeAirportTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "volume", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsVolumeAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsVolumeCountryTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "volume", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsVolumeCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsVolumeContinentTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "volume", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsVolumeContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsVolumeRegionTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "volume", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsVolumeRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsWeightAirportTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "weight", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsWeightCountryTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "weight", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsWeightCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsWeightContinentTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "weight", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsWeightContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsWeightRegionTest() throws Exception {
        Object[] mockObject = {"BOM0677I", "weight", "BOM0677I", 2, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsWeightRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsCPSExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsBookingAirport(any(), any(), any(), any()))
                .thenThrow(new CpsException("Mocked exception message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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
    void getTopAccountsExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopAgentsBookingAirport(any(), any(), any(), any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-accounts")
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