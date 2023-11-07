package com.unisys.trans.cps.middleware.services.pointOfSalesService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
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

    @Override
    public List<PointOfSalesResponseDTO> getPointOfSales(AirlineDashboardRequest airlineDashboardRequest) {


        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnight);

        List<PointOfSalesResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> posObjects;

        if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = advanceFunctionAuditRepository.getPointOfSalesWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
            }
        }
        return response;
    }

    private void buildPOSResponseDTO(List<PointOfSalesResponseDTO> response, List<Object[]> posObjects, String startDate, String endDate, String valueType) {
        if(posObjects != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<String> data = new ArrayList<>();
            for (Object[] array : posObjects) {
                PointOfSalesResponseDTO aPointOfSalesResponseDTO = new PointOfSalesResponseDTO();
                LocalDateTime localDateTime = (LocalDateTime)  array[0];
                String formattedDate = localDateTime.format(formatter);
                aPointOfSalesResponseDTO.setEventDate(formattedDate);
                data.add(formattedDate);
                Number value = (Number) array[1];
                aPointOfSalesResponseDTO.setValue(value.longValue());
                aPointOfSalesResponseDTO.setValueType(valueType);
                response.add(aPointOfSalesResponseDTO);
            }
            // Iterate through the date range and create an empty object for missing dates
            LocalDate currentDate = LocalDate.parse(startDate, formatter);
            LocalDate endDateParsed = LocalDate.parse(endDate, formatter);
            while (!currentDate.isAfter(endDateParsed)) {
                String currentDateStr = currentDate.format(formatter);
                if (!data.contains(currentDateStr)) {
                    PointOfSalesResponseDTO aPointOfSalesResponseDTO = new PointOfSalesResponseDTO();
                    aPointOfSalesResponseDTO.setEventDate(currentDateStr);
                    aPointOfSalesResponseDTO.setValue(AirlineDashboardConstants.LONG_ZERO);
                    aPointOfSalesResponseDTO.setValueType(valueType);
                    response.add(aPointOfSalesResponseDTO);
                }
                // Move to the next date
                currentDate = currentDate.plusDays(1);
            }

        }
    }
}
