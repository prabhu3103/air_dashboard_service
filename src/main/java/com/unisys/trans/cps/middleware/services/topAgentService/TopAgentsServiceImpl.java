package com.unisys.trans.cps.middleware.services.topAgentService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;
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
public class TopAgentsServiceImpl implements TopAgentsService{

    @Autowired
    AuditRequestRepository auditRequestRepository;

    @Override
    public List<TopAgentsResponseDTO> getTopAgents(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnight);

        List<TopAgentsResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;

        if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION);
                }
            }
        }
        return response;
    }

    private void buildTopAgentsResponseDTO(List<TopAgentsResponseDTO> response, List<Object[]> topObjects, String valueType) {
        for (Object[] array : topObjects) {
            TopAgentsResponseDTO topAgentsResponseDTO = new TopAgentsResponseDTO();
            topAgentsResponseDTO.setBranchId((Long) array[0]);
            topAgentsResponseDTO.setCarrier((String) array[1]);
            topAgentsResponseDTO.setCorporation((String) array[2]);
            Number value = (Number) array[3];
            topAgentsResponseDTO.setTotalNoOfCounts(value.longValue());
            response.add(topAgentsResponseDTO);
        }
    }
}
