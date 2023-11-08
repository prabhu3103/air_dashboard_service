package com.unisys.trans.cps.middleware.services.topProductService;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopProductResponseDTO;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
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
public class TopProductsServiceImpl implements TopProductsService {

    @Autowired
    AdvanceFunctionAuditRepository auditRequestRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public List<TopProductResponseDTO> getTopProducts(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.now();

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnight);

        List<TopProductResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;
        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {

                        topObjects = auditRequestRepository.getTopProductsBookingAirport(startDate, endDate,airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopProductsBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopProductsBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopProductsBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects);
                    }
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopProductsWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopProductsWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopProductsWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopProductsWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = auditRequestRepository.getTopProductsVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = auditRequestRepository.getTopProductsVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = auditRequestRepository.getTopProductsVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = auditRequestRepository.getTopProductsVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        buildResponseDTO(response, topObjects,AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                }
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new CpsException(exception.getMessage());
        }
        return response;
    }

    private void buildResponseDTO(List<TopProductResponseDTO> response, List<Object[]> topObjects, String valueType, String stdUnit) {
        for (Object[] array : topObjects) {
            TopProductResponseDTO topProductResponseDTO = new TopProductResponseDTO();
            topProductResponseDTO.setProductCode((String) array[0]);
            topProductResponseDTO.setProductDescription((String) array[1]);
            Number value = (Number) array[2];
            topProductResponseDTO.setValue(value.longValue());
            topProductResponseDTO.setValueType(valueType);
            topProductResponseDTO.setUnit(stdUnit);
            topProductResponseDTO.setMomData(1.1f);
            topProductResponseDTO.setYoyData(-1.1f);
            response.add(topProductResponseDTO);
        }
    }

    private void buildResponseDTO(List<TopProductResponseDTO> response, List<Object[]> topObjects) {
        for (Object[] array : topObjects) {
            TopProductResponseDTO topProductResponseDTO = new TopProductResponseDTO();
            topProductResponseDTO.setProductCode((String) array[0]);
            topProductResponseDTO.setProductDescription((String) array[1]);
            Number value = (Number) array[2];
            topProductResponseDTO.setValue(value.longValue());
            topProductResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
            topProductResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
            topProductResponseDTO.setMomData(1.1f);
            topProductResponseDTO.setYoyData(-1.1f);
            response.add(topProductResponseDTO);
        }
    }
}
