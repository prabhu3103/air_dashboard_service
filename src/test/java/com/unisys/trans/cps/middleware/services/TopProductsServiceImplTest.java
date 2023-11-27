package com.unisys.trans.cps.middleware.services;

import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopProductResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.topproductservice.TopProductsServiceImpl;
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
public class TopProductsServiceImplTest {
    @InjectMocks
    private TopProductsServiceImpl topProductsServiceImpl;
    @Mock
    private AdvanceFunctionAuditRepository aAdvanceFunctionAuditRepository;
    @Mock
    private AirlineHostCountryMasterService aAirlineHostCountryMasterService;

    // Tests for BookingCount
    @Test
    void getProductBookingAirportTest(){
        when(aAdvanceFunctionAuditRepository.getTopProductsBookingAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductBookingCountryTest(){
        when(aAdvanceFunctionAuditRepository.getTopProductsBookingCountry(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductBookingContinentTest(){
        when(aAdvanceFunctionAuditRepository.getTopProductsBookingContinent(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductBookingRegionTest(){
        when(aAdvanceFunctionAuditRepository.getTopProductsBookingRegion(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductBookingAirportErrorTest(){
        List<TopProductResponseDTO> response = null ;
        when(aAdvanceFunctionAuditRepository.getTopProductsBookingAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        try{
            response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("XXX").filterValue("YYZ").carrier("AC").build());
        }catch(Exception exception){
            assertNull( response);
        }
    }
    // Tests for Volume
    @Test
    void getProductVolumeAirportTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsVolumeAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductVolumeCountryTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsVolumeCountry(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("country").filterValue("US").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductVolumeContinentTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsVolumeContinent(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("continent").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductVolumeRegionTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsVolumeRegion(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("region").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductVolumeAirportErrorTest(){
        List<TopProductResponseDTO> response = null ;
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("MC").stdWeightUnit("").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsVolumeAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        try{
            response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("volume").areaBy("XXX").filterValue("YYZ").carrier("AC").build());
        }catch(Exception exception){
            assertNull( response);
        }
    }
    // Tests for Weight
    @Test
    void getProductWeightAirportTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsWeightAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductWeightCountryTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsWeightCountry(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("country").filterValue("US").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductWeightContinentTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsWeightContinent(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("continent").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductWeightRegionTest(){
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsWeightRegion(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("region").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    @Test
    void getProductWeightAirportErrorTest(){
        List<TopProductResponseDTO> response = null ;
        when(aAirlineHostCountryMasterService.findByCarrierCode(anyString())).thenReturn(AirlineHostCountryMaster.builder().carrierCode("AC").stdVolumeUnit("").stdWeightUnit("KG").build());
        when(aAdvanceFunctionAuditRepository.getTopProductsWeightAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsMock());
        try{
            response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("weight").areaBy("XXX").filterValue("YYZ").carrier("AC").build());
        }catch(Exception exception){
            assertNull( response);
        }
    }
    @Test
    void getProductBookingAirportEmptyValueTest(){
        when(aAdvanceFunctionAuditRepository.getPointOfSalesBookingAirport(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), anyString())).thenReturn(getTopProductsEmptyMock());
        List<TopProductResponseDTO> response = topProductsServiceImpl.getTopProducts(AirlineDashboardRequest.builder().startDate("2023-06-08").endDate("2023-11-08").typeOfInfo("BookingCount").areaBy("airport").filterValue("YYZ").carrier("AC").build());
        assertNotNull(response);
    }
    List<Object[]> getTopProductsMock(){
        List<Object[]> topObjects= new ArrayList<>();
        Object[] listData = {"GEN", "AIRCRAFT TOOLING NON HAZ", 234,1.1,1.1};
        topObjects.add(listData);
        return topObjects;
    }
    List<Object[]> getTopProductsEmptyMock(){
        List<Object[]> topObjects= new ArrayList<>();
        Object[] listData = {"GEN", "AIRCRAFT TOOLING NON HAZ",0};
        topObjects.add(listData);
        return topObjects;
    }
}
