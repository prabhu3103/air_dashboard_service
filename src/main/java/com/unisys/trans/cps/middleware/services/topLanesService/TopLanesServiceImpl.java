package com.unisys.trans.cps.middleware.services.topLanesService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopLanesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AuditRequestRepository;
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
public class TopLanesServiceImpl implements TopLanesService {

    @Autowired
    private AuditRequestRepository auditRequestRepository;

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
                        topObjects = auditRequestRepository.getTopLanesBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopLanesBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopLanesBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopLanesBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopLanesWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopLanesWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopLanesWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopLanesWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                }
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new CpsException(exception.getMessage());
        }
        return response;
    }

    private void buildResponseDTO(List<TopLanesResponseDTO> response, List<Object[]> topObjects, String valueType) {
        TopLanesResponseDTO topLanesResponseDTO = new TopLanesResponseDTO();
        if(topObjects != null) {
            for (Object[] array : topObjects) {
                topLanesResponseDTO.setOrigin((String) array[0]);
                topLanesResponseDTO.setDestination((String) array[1]);
                Number value = (Number) array[2];
                topLanesResponseDTO.setValue(value.longValue());
                topLanesResponseDTO.setValueType(valueType);
            }
        }
        response.add(topLanesResponseDTO);
    }
}
