package com.unisys.trans.cps.middleware.services.topLanesService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopLanesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AuditRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TopLanesServiceImpl implements TopLanesService {

    @Autowired
    private AuditRequestRepository auditRequestRepository;

    @Override
    public List<TopLanesResponseDTO> getTopLanes(AirlineDashboardRequest airlineDashboardRequest) {

        List<TopLanesResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;
        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopLanesBookingAirport(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopLanesBookingCountry(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopLanesBookingContinent(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopLanesBookingRegion(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_BOOKING);
                    }
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopLanesWeightAirport(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopLanesWeightCountry(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopLanesWeightContinent(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopLanesWeightRegion(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT);
                    }
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeAirport(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeCountry(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeContinent(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
                        buildResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopLanesVolumeRegion(airlineDashboardRequest.getStartDate(), airlineDashboardRequest.getEndDate(), airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getLocation());
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
