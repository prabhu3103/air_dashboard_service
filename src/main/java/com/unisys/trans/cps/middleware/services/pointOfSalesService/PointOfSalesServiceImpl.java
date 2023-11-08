package com.unisys.trans.cps.middleware.services.pointOfSalesService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class PointOfSalesServiceImpl implements PointOfSalesService {

    @Autowired
    AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public PointOfSalesResponseDTO getPointOfSales(AirlineDashboardRequest airlineDashboardRequest) {


        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnight);

        PointOfSalesResponseDTO response = new PointOfSalesResponseDTO();
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
            }
        }
        return response;
    }

    private void buildPOSResponseDTO(PointOfSalesResponseDTO response, List<Object[]> posObjects, String startDate, String endDate, String valueType, String standardUnit) {
        if(posObjects != null){
            List<POSResponseDTO> aPOSResponseDTOList =  new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Long totalValue = 0L;
            List<String> data = new ArrayList<>();
            for (Object[] array : posObjects) {
                POSResponseDTO aPOSResponseDTO = new POSResponseDTO();
                LocalDateTime localDateTime = (LocalDateTime)  array[0];
                String formattedDate = localDateTime.format(formatter);
                aPOSResponseDTO.setEventDate(formattedDate);
                data.add(formattedDate);
                if(array[1] != null) {
                    Number value = (Number) array[1];
                    aPOSResponseDTO.setValue(value.longValue());
                    totalValue += value.longValue();
                }else {
                    aPOSResponseDTO.setValue(AirlineDashboardConstants.LONG_ZERO);
                }
                aPOSResponseDTO.setValueType(valueType);
                if(standardUnit != null){
                    aPOSResponseDTO.setUnit(standardUnit);
                }else{
                    aPOSResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
                }
                aPOSResponseDTO.setYoyData(AirlineDashboardConstants.DEFAULT_VALUE);
                aPOSResponseDTO.setMomData(AirlineDashboardConstants.DEFAULT_VALUE);
                aPOSResponseDTOList.add(aPOSResponseDTO);
            }
            // Iterate through the date range and create an empty object for missing dates
            LocalDate currentDate = LocalDate.parse(startDate, formatter);
            LocalDate endDateParsed = LocalDate.parse(endDate, formatter);
            while (!currentDate.isAfter(endDateParsed)) {
                String currentDateStr = currentDate.format(formatter);
                if (!data.contains(currentDateStr)) {
                    POSResponseDTO aPOSResponseDTO = new POSResponseDTO();
                    aPOSResponseDTO.setEventDate(currentDateStr);
                    aPOSResponseDTO.setValue(AirlineDashboardConstants.LONG_ZERO);
                    aPOSResponseDTO.setYoyData(AirlineDashboardConstants.DEFAULT_VALUE);
                    aPOSResponseDTO.setMomData(AirlineDashboardConstants.DEFAULT_VALUE);
                    aPOSResponseDTO.setValueType(valueType);
                    aPOSResponseDTOList.add(aPOSResponseDTO);
                }
                // Move to the next date
                currentDate = currentDate.plusDays(1);
            }
            response.setTotalValue(totalValue);
            response.setPosList(aPOSResponseDTOList);

        }
    }
}
