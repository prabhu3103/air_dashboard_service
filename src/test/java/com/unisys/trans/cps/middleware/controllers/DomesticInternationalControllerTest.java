package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import com.unisys.trans.cps.middleware.services.topdomesticinternationalservice.TopDomesticInternationalService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class DomesticInternationalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @InjectMocks
    private DomesticInternationalController operationalDashboardController;

    @Mock
    private TopDomesticInternationalService topDomesticInternationalService;

    @MockBean
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;

    @Test
    void getTopDomesticInternationalBookingAirportTest() throws Exception {

        Object[] mockObject = {"true", 1, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalBookingCountryTest() throws Exception {
        Object[] mockObject = {"true", 1, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalBookingCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalBookingContinentTest() throws Exception {
        Object[] mockObject = {"true", 1, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalBookingContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalBookingRegionTest() throws Exception {
        Object[] mockObject = {"true", 1, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalBookingRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalVolumeAirportTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalVolumeAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalVolumeCountryTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalVolumeCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalVolumeContinentTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalVolumeContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalVolumeRegionTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalVolumeRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalWeightAirportTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalWeightCountryTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalWeightCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalWeightContinentTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalWeightContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getTopDomesticInternationalWeightRegionTest() throws Exception {
        Object[] mockObject = {"true", 1, 1, null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalWeightRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getDomesticInternationalCPSExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(any(), any(), any(), any()))
                .thenThrow(new CpsException("Mocked exception message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
    void getDomesticInternationalExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(any(), any(), any(), any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/domestic-international")
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
