package com.unisys.trans.cps.middleware.services;

import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.pointofsalesservice.PointOfSalesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PointOfSalesServiceImplTest {

    @InjectMocks
    private PointOfSalesServiceImpl aPointOfSalesServiceImpl;

    @Mock
    private AdvanceFunctionAuditRepository aAdvanceFunctionAuditRepository;

    @Mock
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;

    // Tests for BookingCount
    @Test
    void getPointOfSalesBookingAirportTest(){
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesBookingCountryTest(){
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingCountry(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("country").filterValue("US").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesBookingContinentTest(){
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingContinent(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("continent").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesBookingRegionTest(){
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingRegion(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("region").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesBookingAirportErrorTest(){

        List<PointOfSalesResponseDTO> response = null ;
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        try{
            response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("XXX").filterValue("YYZ").carrier("AC").build());
        }catch(Exception exception){
            assertNull( response);
        }
    }
    // Tests for Volume
    @Test
    void getPointOfSalesVolumeAirportTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesVolumeCountryTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeCountry(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("country").filterValue("US").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesVolumeContinentTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeContinent(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("continent").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesVolumeRegionTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeRegion(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("region").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesVolumeAirportErrorTest(){
        List<PointOfSalesResponseDTO> response = null ;
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesVolumeAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        try{
            response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("XXX").filterValue("YYZ").carrier("AC").build());
        }catch(Exception exception){
            assertNull( response);
        }
    }

    // Tests for Weight
    @Test
    void getPointOfSalesWeightAirportTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesWeightAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesWeightCountryTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesWeightCountry(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("country").filterValue("US").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesWeightContinentTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesWeightContinent(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("continent").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesWeightRegionTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesWeightRegion(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("region").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    @Test
    void getPointOfSalesWeightAirportErrorTest(){
        List<PointOfSalesResponseDTO> response = null ;
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getPointOfSalesWeightAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMock());
        try{
            response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("XXX").filterValue("YYZ").carrier("AC").build());
        }catch(Exception exception){
            assertNull( response);
        }
    }

    @Test
    void getPointOfSalesBookingAirportEmptyValueTest(){
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getPointOfSalesMockForEmptyValue());
        List<PointOfSalesResponseDTO> response = aPointOfSalesServiceImpl.getPointOfSales(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }

    List<Object[]> getPointOfSalesMock(){
        List<Object[]> mockedPosList = new ArrayList<>();

        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 9, 15, 10, 30), 100.0f});
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 10, 28, 14, 45), 200.0f});
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 11, 10, 9, 0), 150.0f});

        return mockedPosList;
    }

    List<Object[]> getPointOfSalesMockForEmptyValue(){
        List<Object[]> mockedPosList = new ArrayList<>();
        mockedPosList.add(new Object[]{LocalDateTime.of(2023, 9, 15, 10, 30), 0});
        return mockedPosList;
    }
}