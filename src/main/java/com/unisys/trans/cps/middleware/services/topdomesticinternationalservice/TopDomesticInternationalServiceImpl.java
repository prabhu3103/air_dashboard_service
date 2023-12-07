package com.unisys.trans.cps.middleware.services.topdomesticinternationalservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.DomesticInternationalResponseDTO;
import com.unisys.trans.cps.middleware.models.response.TopDomesticInternationalResponseDTO;
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
public class TopDomesticInternationalServiceImpl implements TopDomesticInternationalService{
    @Autowired
    AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public DomesticInternationalResponseDTO getTopDomesticInternational(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnightMinusOne = LocalTime.MIDNIGHT.minusMinutes(1L);

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnightMinusOne);

        DomesticInternationalResponseDTO response = new DomesticInternationalResponseDTO();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;

        try {
        if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects);
                }
                default ->
                        throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
            AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME, masterRecord.getStdVolumeUnit());
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME, masterRecord.getStdVolumeUnit());
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME, masterRecord.getStdVolumeUnit());
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION, masterRecord.getStdVolumeUnit());
                }
                default ->
                        throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
            AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit());
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit());
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit());
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION, masterRecord.getStdWeightUnit());
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

    private void buildTopDomesticInternationalResponseDTO(DomesticInternationalResponseDTO response, List<Object[]> topObjects, String valueType, String stdUnit) {
        List<TopDomesticInternationalResponseDTO> topDomesticInternationalResponseDTOS = new ArrayList<>();
        Float totalValue = AirlineDashboardConstants.FLOAT_ZERO;
        for (Object[] array : topObjects) {
            TopDomesticInternationalResponseDTO topDomesticInternationalResponseDTO = new TopDomesticInternationalResponseDTO();
            String intl = (String)array[0];
            topDomesticInternationalResponseDTO.setIntl(Boolean.valueOf(intl));
            if(array[2] !=null) {
                Number value = (Number) array[2];
                topDomesticInternationalResponseDTO.setValue(value.floatValue());
                totalValue+=value.floatValue();
            }
            else{
                topDomesticInternationalResponseDTO.setValue(AirlineDashboardConstants.FLOAT_ZERO);
            }
            topDomesticInternationalResponseDTO.setValueType(valueType);
            topDomesticInternationalResponseDTO.setUnit(stdUnit);
            if(array[3] != null){
                Number mom = (Number) array[3];
                topDomesticInternationalResponseDTO.setMomData(mom.floatValue());
            }else{
                topDomesticInternationalResponseDTO.setMomData(0.0f);
            }
            if(array[4] != null){
                Number yoy = (Number) array[4];
                topDomesticInternationalResponseDTO.setYoyData(yoy.floatValue());
            }else{
                topDomesticInternationalResponseDTO.setYoyData(0.0f);
            }
            topDomesticInternationalResponseDTOS.add(topDomesticInternationalResponseDTO);
        }
        response.setTopDomesticInternationalResponseDTOList(topDomesticInternationalResponseDTOS);
        response.setTotalValue(totalValue);
    }

    private void buildTopDomesticInternationalResponseDTO(DomesticInternationalResponseDTO response, List<Object[]> topObjects) {

        if(topObjects != null) {
            List<TopDomesticInternationalResponseDTO> topDomesticInternationalResponseDTOS = new ArrayList<>();
            Long totalValue = 0L;

            for (Object[] array : topObjects) {
                TopDomesticInternationalResponseDTO topDomesticInternationalResponseDTO = new TopDomesticInternationalResponseDTO();
                String intl = (String)array[0];
                topDomesticInternationalResponseDTO.setIntl(Boolean.valueOf(intl));
                Number value = (Number) array[1];
                topDomesticInternationalResponseDTO.setValue(value.floatValue());
                topDomesticInternationalResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
                topDomesticInternationalResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
                totalValue+=value.longValue();
                if(array[3] != null){
                    Number mom = (Number) array[3];
                    topDomesticInternationalResponseDTO.setMomData(mom.floatValue());
                }else{
                    topDomesticInternationalResponseDTO.setMomData(0.0f);
                }
                if(array[4] != null){
                    Number yoy = (Number) array[4];
                    topDomesticInternationalResponseDTO.setMomData(yoy.floatValue());
                }else{
                    topDomesticInternationalResponseDTO.setYoyData(0.0f);
                }
                topDomesticInternationalResponseDTOS.add(topDomesticInternationalResponseDTO);

            }
            response.setTopDomesticInternationalResponseDTOList(topDomesticInternationalResponseDTOS);
            response.setTotalValue(totalValue);
        }
    }

}
