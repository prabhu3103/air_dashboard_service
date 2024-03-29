package com.unisys.trans.cps.middleware.services.topproductservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopProductResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class TopProductsServiceImpl implements TopProductsService {

    @Autowired
    AdvanceFunctionAuditRepository auditRequestRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public List<TopProductResponseDTO> getTopProducts(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnightMinusOne = LocalTime.MIDNIGHT.minusMinutes(1L);

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnightMinusOne);


        List<TopProductResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;
        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)) {
                                topObjects = auditRequestRepository.getTopProductsBookingAirportForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                            else{
                                topObjects = auditRequestRepository.getTopProductsBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsBookingCountryForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                             else {
                                topObjects = auditRequestRepository.getTopProductsBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                             }
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsBookingContinentForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                            else {
                                topObjects = auditRequestRepository.getTopProductsBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsBookingRegionForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                            else {
                                topObjects = auditRequestRepository.getTopProductsBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects);
                            }
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsWeightAirportForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                            }
                            else {
                                topObjects = auditRequestRepository.getTopProductsWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit());
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsWeightCountryForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                            }
                            else{
                                topObjects = auditRequestRepository.getTopProductsWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsWeightContinentForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                            }
                            else {
                                topObjects = auditRequestRepository.getTopProductsWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit());
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsWeightRegionForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                            }
                            else {
                                topObjects = auditRequestRepository.getTopProductsWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit());
                            }
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsVolumeAirportForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                            else{
                                topObjects = auditRequestRepository.getTopProductsVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsVolumeCountryForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                            else{
                                topObjects = auditRequestRepository.getTopProductsVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsVolumeContinentForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                            else{
                                topObjects = auditRequestRepository.getTopProductsVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                            if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AA_CARRIER)){
                                topObjects = auditRequestRepository.getTopProductsVolumeRegionForAA(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                            else{
                                topObjects = auditRequestRepository.getTopProductsVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                                buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                            }
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new CpsException(exception.getMessage());
        }
        return response;
    }

    private void buildResponseDTO(List<TopProductResponseDTO> response, List<Object[]> topObjects, String valueType, String stdUnit) {
        for (Object[] array : topObjects) {
            TopProductResponseDTO topProductResponseDTO = new TopProductResponseDTO();
            topProductResponseDTO.setProductCode((String) array[0]);
            topProductResponseDTO.setProductDescription((String) array[1]);
            Number value = (Number) array[2];
            topProductResponseDTO.setValue(value.floatValue());
            topProductResponseDTO.setValueType(valueType);
            topProductResponseDTO.setUnit(stdUnit);
            if(array[3] != null){
                Number mom = (Number) array[3];
                topProductResponseDTO.setMomData(mom.floatValue());
            }else{
                topProductResponseDTO.setMomData(0.0f);
            }
            if(array[4] != null){
                Number yoy = (Number) array[4];
                topProductResponseDTO.setYoyData(yoy.floatValue());
            }else{
                topProductResponseDTO.setYoyData(0.0f);
            }
            response.add(topProductResponseDTO);
        }
    }

    private void buildResponseDTO(List<TopProductResponseDTO> response, List<Object[]> topObjects) {
        for (Object[] array : topObjects) {
            TopProductResponseDTO topProductResponseDTO = new TopProductResponseDTO();
            topProductResponseDTO.setProductCode((String) array[0]);
            topProductResponseDTO.setProductDescription((String) array[1]);
            Number value = (Number) array[2];
            topProductResponseDTO.setValue(value.floatValue());
            topProductResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
            topProductResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
            if(array[3] != null){
                Number mom = (Number) array[3];
                topProductResponseDTO.setMomData(mom.floatValue());
            }else{
                topProductResponseDTO.setMomData(0.0f);
            }
            if(array[4] != null){
                Number yoy = (Number) array[4];
                topProductResponseDTO.setYoyData(yoy.floatValue());
            }else{
                topProductResponseDTO.setYoyData(0.0f);
            }
            response.add(topProductResponseDTO);
        }
    }
}
