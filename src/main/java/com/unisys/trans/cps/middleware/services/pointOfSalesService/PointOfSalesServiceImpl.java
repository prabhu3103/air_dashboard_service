package com.unisys.trans.cps.middleware.services.pointOfSalesService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.PointOfSalesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AuditRequestRepository;
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
    AuditRequestRepository auditRequestRepository;

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
                    posObjects = auditRequestRepository.getPointOfSalesBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = auditRequestRepository.getPointOfSalesBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = auditRequestRepository.getPointOfSalesBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = auditRequestRepository.getPointOfSalesBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = auditRequestRepository.getPointOfSalesVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = auditRequestRepository.getPointOfSalesVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = auditRequestRepository.getPointOfSalesVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = auditRequestRepository.getPointOfSalesVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    posObjects = auditRequestRepository.getPointOfSalesWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    posObjects = auditRequestRepository.getPointOfSalesWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    posObjects = auditRequestRepository.getPointOfSalesWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    posObjects = auditRequestRepository.getPointOfSalesWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    buildPOSResponseDTO(response, posObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
            }
        }
        return response;
    }

    private void buildPOSResponseDTO(List<PointOfSalesResponseDTO> response, List<Object[]> posObjects, String valueType) {
        if(posObjects != null){
            for (Object[] array : posObjects) {
                PointOfSalesResponseDTO aPointOfSalesResponseDTO = new PointOfSalesResponseDTO();
                LocalDateTime localDateTime = (LocalDateTime)  array[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = localDateTime.format(formatter);
                aPointOfSalesResponseDTO.setEventDate(formattedDate);
                Number value = (Number) array[1];
                aPointOfSalesResponseDTO.setValue(value.longValue());
                aPointOfSalesResponseDTO.setValueType(valueType);
                response.add(aPointOfSalesResponseDTO);
            }
        }
    }
}
