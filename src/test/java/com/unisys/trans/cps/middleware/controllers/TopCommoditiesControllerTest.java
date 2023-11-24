package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.repository.AFKLCommodityProductRepository;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.repository.CommodityRepository;
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
class TopCommoditiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @MockBean
    private CommodityRepository commodityRepository;

    @MockBean
    private AFKLCommodityProductRepository afklCommodityProductRepository;


    @MockBean
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;

    @Test
    void getTopCommodityBookingAirportTest() throws Exception{
        Object[] mockObject = {"0000", 29, "BookingCount", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityBookingAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","bookingcount")
                        .queryParam("areaBy","airport")
                        .queryParam("filterValue","BLR")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityDescriptionTest() throws Exception{
        Object[] mockObject = {"0007", "MIXED PRODUCE"};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(afklCommodityProductRepository.getTopAFKLCommodityDescription (any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","bookingcount")
                        .queryParam("areaBy","airport")
                        .queryParam("filterValue","AKL")
                        .queryParam("carrier","NZ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopAFKLCommodityDescriptionTest() throws Exception{
        Object[] mockObject = {"ACFT", "AIRCRAFT PARTS"};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityVolumeRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","bookingcount")
                        .queryParam("areaBy","airport")
                        .queryParam("filterValue","AMS")
                        .queryParam("carrier","AF-KL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityBookingCountryTest() throws Exception{
        Object[] mockObject = {"0000", 29, "BookingCount", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityBookingCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","bookingcount")
                        .queryParam("areaBy","country")
                        .queryParam("filterValue","IN")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityBookingContinentTest() throws Exception{
        Object[] mockObject = {"0000", 29, "GENERAL FREIGHT OF ALL KINDS", "BookingCount", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityBookingContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","bookingcount")
                        .queryParam("areaBy","continent")
                        .queryParam("filterValue","AP")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityBookingRegionTest() throws Exception{
        Object[] mockObject = {"0000", 29, "BookingCount", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityBookingRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","bookingcount")
                        .queryParam("areaBy","region")
                        .queryParam("filterValue","APAC")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityWeightAirportTest() throws Exception{
        Object[] mockObject = {"0000", 9508.00, "weight", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","weight")
                        .queryParam("areaBy","airport")
                        .queryParam("filterValue","BLR")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityWeightCountryTest() throws Exception{
        Object[] mockObject = {"0000", 9508.00, "weight", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityWeightCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","weight")
                        .queryParam("areaBy","country")
                        .queryParam("filterValue","IN")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityWeightContinentTest() throws Exception{
        Object[] mockObject = {"0000", 9508.00, "weight", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityWeightContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","weight")
                        .queryParam("areaBy","continent")
                        .queryParam("filterValue","AP")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityWeightRegionTest() throws Exception{
        Object[] mockObject = {"0000", 9508.00, "weight", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("").stdWeightUnit("KG").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityWeightRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","weight")
                        .queryParam("areaBy","region")
                        .queryParam("filterValue","APAC")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityVolumeAirportTest() throws Exception{
        Object[] mockObject = {"0000", 566.92, "volume", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityVolumeAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","volume")
                        .queryParam("areaBy","airport")
                        .queryParam("filterValue","BLR")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityVolumeCountryTest() throws Exception{
        Object[] mockObject = {"0000", 566.92, "volume", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityVolumeCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","volume")
                        .queryParam("areaBy","country")
                        .queryParam("filterValue","IN")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityVolumeContinentTest() throws Exception{
        Object[] mockObject = {"0000", 566.92, "volume", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityVolumeContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","volume")
                        .queryParam("areaBy","continent")
                        .queryParam("filterValue","AP")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityVolumeRegionTest() throws Exception{
        Object[] mockObject = {"0000", 566.92, "volume", null, 1.1, 1.1};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AI").stdVolumeUnit("MC").stdWeightUnit("").build());
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityVolumeRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate",  "2023-01-01")
                        .queryParam("typeOfInfo","volume")
                        .queryParam("areaBy","region")
                        .queryParam("filterValue","APAC")
                        .queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getTopCommodityCPSExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityBookingAirport(any(), any(), any(), any()))
                .thenThrow(new CpsException("Mocked exception message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
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
    void getTopCommodityExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopCommodityBookingAirport(any(), any(), any(), any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-commodities")
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
