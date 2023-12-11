package com.unisys.trans.cps.middleware.services.topagentservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.AgentResponseDTO;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;
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
public class TopAgentsServiceImpl implements TopAgentsService{

    @Autowired
    AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public AgentResponseDTO getTopAccounts(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnightMinusOne = LocalTime.MIDNIGHT.minusMinutes(1L);

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnightMinusOne);

        AgentResponseDTO response = new AgentResponseDTO();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;

        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, airlineDashboardRequest.getCarrier());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }
            }

            else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit(), airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit(), airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit(), airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION,masterRecord.getStdVolumeUnit(), airlineDashboardRequest.getCarrier());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }
            }

            else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit(), airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit(), airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit(), airlineDashboardRequest.getCarrier());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopAgentsWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION,masterRecord.getStdWeightUnit(), airlineDashboardRequest.getCarrier());
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

    private void buildTopAgentsResponseDTO(AgentResponseDTO response, List<Object[]> topObjects, String valueType, String stdUnit, String carrierCode) {
        List<TopAgentsResponseDTO> topAgentsResponseDTOS = new ArrayList<>();
        Object[] newAccounts = advanceFunctionAuditRepository.getNewAgentsInCurrentMonth(LocalDateTime.now(), carrierCode);
        if(topObjects != null) {
            for (Object[] array : topObjects) {
                TopAgentsResponseDTO topAgentsResponseDTO = new TopAgentsResponseDTO();
                topAgentsResponseDTO.setAccNo((String) array[1]);
                Number value = (Number) array[2];
                topAgentsResponseDTO.setValue(value.floatValue());
                topAgentsResponseDTO.setValueType(valueType);
                topAgentsResponseDTO.setUnit(stdUnit);
                if(array[3] != null){
                    Number mom = (Number) array[3];
                    topAgentsResponseDTO.setMomData(mom.floatValue());
                }else{
                    topAgentsResponseDTO.setMomData(0.0f);
                }
                if(array[4] != null){
                    Number yoy = (Number) array[4];
                    topAgentsResponseDTO.setYoyData(yoy.floatValue());
                }else{
                    topAgentsResponseDTO.setYoyData(0.0f);
                }
                topAgentsResponseDTOS.add(topAgentsResponseDTO);
            }
            response.setTopAgentsResponseDTOList(topAgentsResponseDTOS);
            Number value = (Number) newAccounts[0];
            response.setNewAccount(value.intValue());
        }
    }

    private void buildTopAgentsResponseDTO(AgentResponseDTO response, List<Object[]> topObjects, String carrierCode) {
        if(topObjects != null) {
            List<TopAgentsResponseDTO> topAgentsResponseDTOS = new ArrayList<>();
            Object[] newAccounts = advanceFunctionAuditRepository.getNewAgentsInCurrentMonth(LocalDateTime.now(), carrierCode);
            for (Object[] array : topObjects) {
                TopAgentsResponseDTO topAgentsResponseDTO = new TopAgentsResponseDTO();
                topAgentsResponseDTO.setAccNo((String) array[1]);
                Number value = (Number) array[2];
                topAgentsResponseDTO.setValue(value.floatValue());
                topAgentsResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
                if(array[3] != null){
                    Number mom = (Number) array[3];
                    topAgentsResponseDTO.setMomData(mom.floatValue());
                }else{
                    topAgentsResponseDTO.setMomData(0.0f);
                }
                if(array[4] != null){
                    Number yoy = (Number) array[4];
                    topAgentsResponseDTO.setYoyData(yoy.floatValue());
                }else{
                    topAgentsResponseDTO.setYoyData(0.0f);
                }
                topAgentsResponseDTOS.add(topAgentsResponseDTO);
            }
            response.setTopAgentsResponseDTOList(topAgentsResponseDTOS);
            Number value = (Number) newAccounts[0];
            response.setNewAccount(value.intValue());
        }
    }
}
