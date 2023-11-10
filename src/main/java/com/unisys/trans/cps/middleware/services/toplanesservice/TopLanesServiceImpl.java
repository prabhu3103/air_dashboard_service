package com.unisys.trans.cps.middleware.services.toplanesservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopLanesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.repository.CityCountryMasterRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class TopLanesServiceImpl implements TopLanesService {

    @Autowired
    private AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Autowired
    CityCountryMasterRepository cityCountryMasterRepository;

    @Override
    public List<TopLanesResponseDTO> getTopLanes(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.now();

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnight);

        List<TopLanesResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;
        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopLanesVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
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

    private void buildResponseDTO(List<TopLanesResponseDTO> response, List<Object[]> topObjects, String valueType, String stdUnit) {

        if(topObjects != null) {
            HashMap<String,String> displayNameMap = getAirportDisplayNames(topObjects);
            for (Object[] array : topObjects) {
                TopLanesResponseDTO topLanesResponseDTO = new TopLanesResponseDTO();
                topLanesResponseDTO.setOrigin((String) array[0]);
                topLanesResponseDTO.setDestination((String) array[1]);
                topLanesResponseDTO.setOrgName(displayNameMap.get((String)array[0]));
                topLanesResponseDTO.setDestName(displayNameMap.get((String)array[1]));
                Number value = (Number) array[2];
                topLanesResponseDTO.setValue(value.longValue());
                topLanesResponseDTO.setValueType(valueType);
                topLanesResponseDTO.setUnit(stdUnit);
                topLanesResponseDTO.setMomData(1.1f);
                topLanesResponseDTO.setYoyData(-1.1f);
                response.add(topLanesResponseDTO);
            }
        }
    }

    private void buildResponseDTO(List<TopLanesResponseDTO> response, List<Object[]> topObjects) {
        if(topObjects != null) {
            HashMap<String,String> displayNameMap = getAirportDisplayNames(topObjects);
            for (Object[] array : topObjects) {
                TopLanesResponseDTO topLanesResponseDTO = new TopLanesResponseDTO();
                topLanesResponseDTO.setOrigin((String) array[0]);
                topLanesResponseDTO.setDestination((String) array[1]);
                topLanesResponseDTO.setOrgName(displayNameMap.get((String)array[0]));
                topLanesResponseDTO.setDestName(displayNameMap.get((String)array[1]));
                Number value = (Number) array[2];
                topLanesResponseDTO.setValue(value.longValue());
                topLanesResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
                topLanesResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
                topLanesResponseDTO.setMomData(1.1f);
                topLanesResponseDTO.setYoyData(-1.1f);
                response.add(topLanesResponseDTO);
            }
        }
    }

    private HashMap<String, String> getAirportDisplayNames(List<Object[]> topObjects) {
        HashMap<String, String> displayNameMap;
        try {
            displayNameMap = new HashMap<>();
            Set<String> airportsList = new HashSet<>();
            for (Object[] array : topObjects) {
                airportsList.add((String) array[0]);
                airportsList.add((String) array[1]);
            }
            List<Object[]> cityCountryMaster = cityCountryMasterRepository.findByCodes(airportsList.stream().toList());
            for (Object[] array : cityCountryMaster) {
                displayNameMap.put((String) array[0], (String) array[1]);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new CpsException(exception.getMessage());
        }
        return displayNameMap;
    }
}
