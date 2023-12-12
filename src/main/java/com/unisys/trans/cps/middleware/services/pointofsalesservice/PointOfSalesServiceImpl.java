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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            float[] maxPosValue = {Float.MIN_VALUE};
            float[] totalValue = {AirlineDashboardConstants.FLOAT_ZERO};
            float[] momValue = {AirlineDashboardConstants.FLOAT_ZERO};
            float[] yoyValue = {AirlineDashboardConstants.FLOAT_ZERO};
            final String[] momYoyFlag = {"TRUE"};
            Map<String, List<POSResponseDTO>> groupedByDate = posObjects.stream()
                    .collect(Collectors.groupingBy(
                            array -> ((Timestamp) array[0]).toLocalDateTime().format(FORMATTER),
                            Collectors.mapping(array -> {
                                POSResponseDTO posResponseDTO = new POSResponseDTO();
                                posResponseDTO.setEventDate(((Timestamp) array[0]).toLocalDateTime().format(FORMATTER));
                                posResponseDTO.setValue(array.length > 1 && array[1] != null ? ((Number) array[1]).floatValue() : AirlineDashboardConstants.LONG_ZERO.floatValue());
                                totalValue[0] += posResponseDTO.getValue();
                                if (posResponseDTO.getValue() > maxPosValue[0]) {
                                    maxPosValue[0] = posResponseDTO.getValue();
                                }
                                if (momYoyFlag[0].equals("TRUE")) {
                                    if (array.length > 2 && array[2] != null) {
                                        momValue[0] = ((Number) array[2]).floatValue();
                                    }
                                    if (array.length > 3 && array[3] != null) {
                                        yoyValue[0] = ((Number) array[3]).floatValue();
                                    }
                                    momYoyFlag[0] = "FALSE";
                                }
                                posResponseDTO.setValueType(valueType);
                                posResponseDTO.setUnit(standardUnit != null ? standardUnit : AirlineDashboardConstants.EMPTY_STRING);
                                return posResponseDTO;
                            }, Collectors.toList())
                    ));

            // Stream to create empty POSResponseDTO for missing dates
            List<POSResponseDTO> missingDatePosDTOs = LocalDate.parse(startDate, FORMATTER)
                    .datesUntil(LocalDate.parse(endDate, FORMATTER).plusDays(1))
                    .filter(date -> !groupedByDate.containsKey(date.format(FORMATTER)))
                    .map(date -> {
                        POSResponseDTO emptyPosResponseDTO = new POSResponseDTO();
                        emptyPosResponseDTO.setEventDate(date.format(FORMATTER));
                        emptyPosResponseDTO.setValue(AirlineDashboardConstants.LONG_ZERO.floatValue());
                        emptyPosResponseDTO.setValueType(valueType);
                        emptyPosResponseDTO.setUnit(standardUnit != null ? standardUnit : AirlineDashboardConstants.EMPTY_STRING);
                        return emptyPosResponseDTO;
                    })
                    .collect(Collectors.toList());

            // Combine the existing POSResponseDTOs with the ones for missing dates
            List<POSResponseDTO> posResponseDTOList = groupedByDate.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            posResponseDTOList.addAll(missingDatePosDTOs);

            // Sort the combined list by event date
            posResponseDTOList.sort(Comparator.comparing(POSResponseDTO::getEventDate));

            PointOfSalesResponseDTO pointOfSalesResponseDTO = new PointOfSalesResponseDTO();
            pointOfSalesResponseDTO.setTotalValue(totalValue[0]);
            pointOfSalesResponseDTO.setMaxValue(maxPosValue[0]);
            pointOfSalesResponseDTO.setYoyData(yoyValue[0]);
            pointOfSalesResponseDTO.setMomData(momValue[0]);
            pointOfSalesResponseDTO.setPosList(posResponseDTOList);

            response.add(pointOfSalesResponseDTO);
        }
    }
}
