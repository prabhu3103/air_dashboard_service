package com.unisys.trans.cps.middleware.services.topcommoditiesservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.TopCommoditiesResponseDTO;
import com.unisys.trans.cps.middleware.repository.AFKLCommodityProductRepository;
import com.unisys.trans.cps.middleware.repository.AdvanceFunctionAuditRepository;
import com.unisys.trans.cps.middleware.repository.CommodityRepository;
import com.unisys.trans.cps.middleware.services.AirlineHostCountryMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TopCommodityServiceImpl implements TopCommodityService{

    @Autowired
    AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    CommodityRepository commodityRepository;

    @Autowired
    AFKLCommodityProductRepository afklCommodityProductRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public List<TopCommoditiesResponseDTO> getTopCommodities(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnightMinusOne = LocalTime.MIDNIGHT.minusMinutes(1L);

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnightMinusOne);

        List<TopCommoditiesResponseDTO> response = new ArrayList<>();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;
        List<Object[]> topObjects1 = null;
        List<String> codes = new LinkedList<>();

        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {
                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1);
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1);
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1);
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1);
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_WEIGHT,masterRecord.getStdWeightUnit());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

            } else if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopCommodityVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        if(topObjects != null  && !topObjects.isEmpty()) {
                            for (Object[] array : topObjects)
                                codes.add((String) array[0]);
                        }
                        if(airlineDashboardRequest.getCarrier().equalsIgnoreCase(AirlineDashboardConstants.AFKL_CARRIER))
                            topObjects1 = afklCommodityProductRepository.getTopAFKLCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        else
                            topObjects1 = commodityRepository.getTopCommodityDescription(codes, airlineDashboardRequest.getCarrier());
                        buildResponseDTO(response, topObjects, topObjects1, AirlineDashboardConstants.INFO_TYPE_VOLUME,masterRecord.getStdVolumeUnit());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new CpsException(exception.getMessage());
        }
        return response;
    }


    private void buildResponseDTO(List<TopCommoditiesResponseDTO> response, List<Object[]> topObjects, List<Object[]> topObjects1){
        Map<String, Number> result1 = new LinkedHashMap<>();
        Map<String, String> result2 = new LinkedHashMap<>();
        Map<String, Number> result3 = new LinkedHashMap<>();
        Map<String, Number> result4 = new LinkedHashMap<>();

        //Merging Empty String commodity count with commodity=0000 and removing commodity=000
        if(topObjects != null && !topObjects.isEmpty()) {
            int addCount = 0;
            for (Object[] array : topObjects) {
                String str = (String) array[0];
                if(!str.equalsIgnoreCase("0000")) {
                    if(!str.equalsIgnoreCase(" ")) {
                        Number value = (Number) array[1];
                        result1.put((String) array[0], value);
                    }else{
                        Number value = (Number) array[1];
                        addCount += value.intValue();
                    }
                }else{
                    Number value = (Number) array[1];
                    addCount += value.intValue();
                }
                if(array[2] != null) {
                    Number mom = (Number) array[2];
                    result3.put((String) array[0], mom);
                } else{
                    result3.put((String) array[0], 0.0f);
                }
                if(array[3] != null) {
                    Number yoy = (Number) array[3];
                    result4.put((String) array[0], yoy);
                }else{
                    result4.put((String) array[0], 0.0f);
                }
            }
            if(addCount > 0)
                result1.put(" ", addCount);
        }

        // Sorting the LinkedHashMap by values in descending order
        result1 = result1.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> ((Number) entry.getValue()).doubleValue(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        //If there are 6 entries, then remove last one
        if (result1.size() == 6) {
            result1.remove(result1.keySet().toArray()[result1.size() - 1]);

        }

        if(topObjects1 != null && !topObjects1.isEmpty()) {
            for (Object[] array : topObjects1) {
                result2.put((String) array[0], (String) array[1]);
            }
        }

        for (String commodity : result1.keySet()) {
            boolean flag = false;
            TopCommoditiesResponseDTO topCommoditiesResponseDTO = new TopCommoditiesResponseDTO();
            String commDiscription = null;

            //Get Discription for each commodity
            if (result2.containsKey(commodity) && commodity != "0000") {
                commDiscription = result2.get(commodity);
                Number value = (Number) result1.get(commodity);
                topCommoditiesResponseDTO.setCommodity(commodity);
                topCommoditiesResponseDTO.setCommodityDesc(commDiscription);
                topCommoditiesResponseDTO.setValue(value.floatValue());
            }

            //if commodity is empty string & commodity is 0000 then set commodity = 0000
            if(commodity.equalsIgnoreCase(" ") || commodity.equalsIgnoreCase("0000")){
                topCommoditiesResponseDTO.setCommodity("0000");
                topCommoditiesResponseDTO.setCommodityDesc("GENERAL CARGO");
                Number value = (Number) result1.get(commodity);
                topCommoditiesResponseDTO.setValue(value.floatValue());
                commodity="0000";

            }  //If given commodity is not present in Commodity table based on Carrier.
            else if(commodity != null && commDiscription == null){
                topCommoditiesResponseDTO.setCommodity(commodity);
                topCommoditiesResponseDTO.setCommodityDesc(commodity);
                Number value = (Number) result1.get(commodity);
                topCommoditiesResponseDTO.setValue(value.floatValue());
            }
            topCommoditiesResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
            topCommoditiesResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
            Number MoM = (Number) result3.get(commodity);
            Number YoY = (Number) result4.get(commodity);
            topCommoditiesResponseDTO.setMomData(MoM.floatValue());
            topCommoditiesResponseDTO.setYoyData(YoY.floatValue());
            response.add(topCommoditiesResponseDTO);
        }
    }

    private void buildResponseDTO(List<TopCommoditiesResponseDTO> response, List<Object[]> topObjects, List<Object[]> topObjects1,
                                  String valueType, String stdUnit) {

        Map<String, Number> result1 = new LinkedHashMap<>();
        Map<String, String> result2 = new LinkedHashMap<>();
        Map<String, Number> result3 = new LinkedHashMap<>();
        Map<String, Number> result4 = new LinkedHashMap<>();

        //Merging Empty String commodity count with commodity=0000 and removing commodity=000
        if(topObjects != null && !topObjects.isEmpty()) {
            float addCount = 0.0f;
            for (Object[] array : topObjects) {
                String str = (String) array[0];
                if(!str.equalsIgnoreCase("0000")) {
                    if(!str.equalsIgnoreCase(" ")) {
                        Number value = (Number) array[1];
                        result1.put((String) array[0], value);
                    }else{
                        Number value = (Number) array[1];
                        addCount += value.floatValue();
                    }
                }else{
                    Number value = (Number) array[1];
                    addCount += value.floatValue();
                }
                if(array[2] != null) {
                    Number mom = (Number) array[2];
                    result3.put((String) array[0], mom);
                } else{
                    result3.put((String) array[0], 0.0f);
                }
                if(array[3] != null) {
                    Number yoy = (Number) array[3];
                    result4.put((String) array[0], yoy);
                }else{
                    result4.put((String) array[0], 0.0f);
                }
            }
            if(addCount > 0)
                result1.put(" ", addCount);
        }

        // Sorting the LinkedHashMap by values in descending order
        result1 = result1.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> ((Number) entry.getValue()).doubleValue(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        //If there are 6 entries, then remove last one
        if (result1.size() == 6) {
            result1.remove(result1.keySet().toArray()[result1.size() - 1]);

        }

        if(topObjects1 != null && !topObjects1.isEmpty()) {
            for (Object[] array : topObjects1)
                result2.put((String) array[0], (String) array[1]);
        }

        for (String commodity : result1.keySet()) {
            TopCommoditiesResponseDTO topCommoditiesResponseDTO = new TopCommoditiesResponseDTO();
            String commDiscription = null;

            //Get Discription for each commodity
            if (result2.containsKey(commodity) && !commodity.equalsIgnoreCase("0000")) {
                commDiscription = result2.get(commodity);
                Number value = (Number) result1.get(commodity);
                topCommoditiesResponseDTO.setCommodity(commodity);
                topCommoditiesResponseDTO.setCommodityDesc(commDiscription);
                topCommoditiesResponseDTO.setValue(value.floatValue());
            }

            //if commodity is empty string & commodity is 0000 then set commodity = 0000
            if(commodity.equalsIgnoreCase(" ") || commodity.equalsIgnoreCase("0000")){
                topCommoditiesResponseDTO.setCommodity("0000");
                topCommoditiesResponseDTO.setCommodityDesc("GENERAL CARGO");
                Number value = (Number) result1.get(commodity);
                topCommoditiesResponseDTO.setValue(value.floatValue());
                commodity="0000";

            }  //If given commodity is not present in Commodity table based on Carrier.
            else if(commodity != null && commDiscription == null){
                topCommoditiesResponseDTO.setCommodity(commodity);
                topCommoditiesResponseDTO.setCommodityDesc(commodity);
                Number value = (Number) result1.get(commodity);
                topCommoditiesResponseDTO.setValue(value.floatValue());
            }
            topCommoditiesResponseDTO.setValueType(valueType);
            topCommoditiesResponseDTO.setUnit(stdUnit);
            Number MoM = (Number) result3.get(commodity);
            Number YoY = (Number) result4.get(commodity);
            topCommoditiesResponseDTO.setMomData(MoM.floatValue());
            topCommoditiesResponseDTO.setYoyData(YoY.floatValue());
            response.add(topCommoditiesResponseDTO);
        }
    }

}
