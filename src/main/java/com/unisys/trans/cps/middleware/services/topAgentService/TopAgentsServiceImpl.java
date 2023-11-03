package com.unisys.trans.cps.middleware.services.topAgentService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopAgentsResponseDTO;
import com.unisys.trans.cps.middleware.repository.AuditRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopAgentsServiceImpl implements TopAgentsService{

    @Autowired
    AuditRequestRepository auditRequestRepository;

    @Override
    public List<TopAgentsResponseDTO> getTopAgents(AirlineDashboardRequest airlineDashboardRequest) {

        List<TopAgentsResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;

        if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingAirport(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingCountry(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingContinent(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = auditRequestRepository.getTopAgentsBookingRegion(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeAirport(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeCountry(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeContinent(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = auditRequestRepository.getTopAgentsVolumeRegion(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.AREA_BY_REGION);
                }
            }
        }

        else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
            switch (areaBy) {
                case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightAirport(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightCountry(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightContinent(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                    buildTopAgentsResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                }
                case AirlineDashboardConstants.AREA_BY_REGION -> {
                    topObjects = auditRequestRepository.getTopAgentsWeightRegion(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
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
