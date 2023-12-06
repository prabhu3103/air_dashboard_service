package com.unisys.trans.cps.middleware.controllers;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.repository.AirlineHostCountryMasterRepository;
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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TopLanesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @MockBean
    private AirlineHostCountryMasterRepository airlineHostCountryMasterRepository;

    @Test
    void getToplanesBookingAirportTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesBookingAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
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
    void getToplanesBookingCountryTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesBookingCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","Country").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesBookingContinentTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesBookingContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","Continent").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesBookingRegionTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesBookingRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","bookingcount").
                        queryParam("areaBy","Region").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesWeightAirportTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("kg");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","Weight").
                        queryParam("areaBy","Airport").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesWeightCountryTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("kg");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightCountry(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","Weight").
                        queryParam("areaBy","Country").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesWeightContinentTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("kg");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightContinent(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","Weight").
                        queryParam("areaBy","continent").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesWeightRegionTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("kg");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightRegion(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","Weight").
                        queryParam("areaBy","Region").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesVolumeAirportTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("mc");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01").
                        queryParam("endDate",  "2023-01-01").
                        queryParam("typeOfInfo","volume").
                        queryParam("areaBy","Airport").
                        queryParam("filterValue","BLR").
                        queryParam("carrier","AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
    }

    @Test
    void getToplanesVolumeCountryTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("mc");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
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
    void getToplanesVolumeContinentTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("mc");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
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
    void getToplanesVolumeRegionTest() throws Exception {

        Object[] mockObject = {"YYZ", "YUL", 2,0.0f,0.0f};
        List<Object[]> mockObjects = new ArrayList<>();
        mockObjects.add(mockObject);
        AirlineHostCountryMaster masterRecord = new AirlineHostCountryMaster();
        masterRecord.setStdWeightUnit("mc");
        List<AirlineHostCountryMaster> masterRecords = new ArrayList<>();
        masterRecords.add(masterRecord);
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any())).thenReturn(mockObjects);
        Mockito.when(airlineHostCountryMasterRepository.findByCarrierCode(any())).thenReturn(masterRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
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
    void getTopLanesCPSExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any()))
                .thenThrow(new CpsException("Mocked exception message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate", "2023-01-01")
                        .queryParam("typeOfInfo", "weight")
                        .queryParam("areaBy", "airport")
                        .queryParam("filterValue", "BLR")
                        .queryParam("carrier", "AI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(false));
    }

    @Test
    void getTopLanesExceptionTest() throws Exception {
        Mockito.when(advanceFunctionAuditRepository.getTopLanesWeightAirport(any(), any(), any(), any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airline-dashboard/top-lanes")
                        .queryParam("startDate", "2010-01-01")
                        .queryParam("endDate", "2023-01-01")
                        .queryParam("typeOfInfo", "weight")
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
