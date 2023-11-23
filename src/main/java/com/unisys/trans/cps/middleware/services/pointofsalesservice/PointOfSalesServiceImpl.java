package com.unisys.trans.cps.middleware.services.pointofsalesservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.POSResponseDTO;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PointOfSalesServiceImpl implements PointOfSalesService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public List<PointOfSalesResponseDTO> getPointOfSales(AirlineDashboardRequest airlineDashboardRequest) {


        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnightMinusOne = LocalTime.MIDNIGHT.minusMinutes(1L);

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnightMinusOne);

        List<PointOfSalesResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> posObjects;

        if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING, null);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING, null);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING, null);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING, null);
                }
                default ->
                        throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
            AirlineHostCountryMaster standardUnit = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME, standardUnit.getStdVolumeUnit());
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME, standardUnit.getStdVolumeUnit());
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME, standardUnit.getStdVolumeUnit());
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME, standardUnit.getStdVolumeUnit());
                }
                default ->
                        throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
            AirlineHostCountryMaster standardUnit = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT, standardUnit.getStdWeightUnit());
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT, standardUnit.getStdWeightUnit());
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT, standardUnit.getStdWeightUnit());
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT, standardUnit.getStdWeightUnit());
                }
                default ->
                        throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
            }
        }
        return response;
    }

    private void buildPOSResponseDTO(List<PointOfSalesResponseDTO> response, List<Object[]> posObjects, String startDate, String endDate, String valueType, String standardUnit) {

        if (posObjects != null && !posObjects.isEmpty()) {
            Map<String, Number> dataMap = posObjects.stream()
                    .collect(Collectors.toMap(
                             array -> ((LocalDateTime) array[0]).format(FORMATTER),
                             array -> (Optional.ofNullable((Number) array[1]).orElse(AirlineDashboardConstants.FLOAT_ZERO))));
            float[] maxPosValue = {Float.MIN_VALUE};
            float[] totalValue = {AirlineDashboardConstants.FLOAT_ZERO};
            List<POSResponseDTO> posResponseDTOList = LocalDate.parse(startDate, FORMATTER)
                    .datesUntil(LocalDate.parse(endDate, FORMATTER).plusDays(1))
                    .map(date -> {
                        String formattedDate = date.format(FORMATTER);
                        Number value = dataMap.getOrDefault(formattedDate, AirlineDashboardConstants.FLOAT_ZERO);
                        float posValue = value.floatValue();
                        totalValue[0] += posValue;
                        POSResponseDTO posResponseDTO = new POSResponseDTO();
                        posResponseDTO.setEventDate(formattedDate);
                        posResponseDTO.setValue(posValue);
                        posResponseDTO.setValueType(valueType);
                        posResponseDTO.setUnit(standardUnit != null ? standardUnit : AirlineDashboardConstants.EMPTY_STRING);
                        if (posValue > maxPosValue[0]) {
                            maxPosValue[0] = posValue;
                        }
                        return posResponseDTO;
                    })
                    .sorted(Comparator.comparing(POSResponseDTO::getEventDate))
                    .toList();

            PointOfSalesResponseDTO pointOfSalesResponseDTO = new PointOfSalesResponseDTO();
            pointOfSalesResponseDTO.setTotalValue(totalValue[0]);
            pointOfSalesResponseDTO.setMaxValue(maxPosValue[0]);
            pointOfSalesResponseDTO.setYoyData(AirlineDashboardConstants.DEFAULT_NEGATIVE_VALUE);
            pointOfSalesResponseDTO.setMomData(AirlineDashboardConstants.DEFAULT_VALUE);
            pointOfSalesResponseDTO.setPosList(posResponseDTOList);

            response.add(pointOfSalesResponseDTO);
        }
    }
}
