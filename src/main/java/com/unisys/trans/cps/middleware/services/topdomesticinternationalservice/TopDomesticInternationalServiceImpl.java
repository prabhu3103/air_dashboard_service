package com.unisys.trans.cps.middleware.services.topdomesticinternationalservice;

import com.unisys.trans.cps.middleware.constants.AirlineDashboardConstants;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.entity.AirlineHostCountryMaster;
import com.unisys.trans.cps.middleware.models.request.AirlineDashboardRequest;
import com.unisys.trans.cps.middleware.models.response.DomesticInternationalResponseDTO;
import com.unisys.trans.cps.middleware.models.response.TopDomesticInternationalResponseDTO;
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
public class TopDomesticInternationalServiceImpl implements TopDomesticInternationalService{
    @Autowired
    AdvanceFunctionAuditRepository advanceFunctionAuditRepository;

    @Autowired
    AirlineHostCountryMasterService masterService;

    @Override
    public DomesticInternationalResponseDTO getTopDomesticInternational(AirlineDashboardRequest airlineDashboardRequest) {

        LocalDate localDateStart = LocalDate.parse(airlineDashboardRequest.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate localDateEnd = LocalDate.parse(airlineDashboardRequest.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnightMinusOne = LocalTime.MIDNIGHT.minusMinutes(1L);

        LocalDateTime startDate = LocalDateTime.of(localDateStart, midnight);
        LocalDateTime endDate = LocalDateTime.of(localDateEnd, midnightMinusOne);

        DomesticInternationalResponseDTO response = new DomesticInternationalResponseDTO();
        String areaBy = airlineDashboardRequest.getAreaBy().toLowerCase();
        List<Object[]> topObjects;

        List<Object[]> currentMonthObjects;
        List<Object[]> previousMonthObjects;
        List<Object[]> previousYearSameMonthObjects;

        LocalDate currentDate = LocalDate.now();

        LocalDate startDateOfCurrentMonth = currentDate.withDayOfMonth(1);
        LocalDateTime startDateOfCurrentMonths = LocalDateTime.of(startDateOfCurrentMonth, midnight);

        LocalDate endDateOfCurrentMonth = currentDate;
        LocalDateTime endDateOfCurrentMonths = LocalDateTime.of(endDateOfCurrentMonth, midnight);

        LocalDate startDateOfPreviousMonth = currentDate.minusMonths(1).withDayOfMonth(1);
        LocalDateTime startDateOfPreviousMonths = LocalDateTime.of(startDateOfPreviousMonth, midnight);

        LocalDate endDateOfPreviousMonth = currentDate.minusMonths(1).withDayOfMonth(
                currentDate.minusMonths(1).lengthOfMonth());
        LocalDateTime endDateOfPreviousMonths = LocalDateTime.of(endDateOfPreviousMonth, midnight);

        LocalDate startDateOfPreviousYearMonth = currentDate.minusYears(1).withDayOfMonth(1);
        LocalDateTime startDateOfPreviousYearMonths = LocalDateTime.of(startDateOfPreviousYearMonth, midnight);

        LocalDate endDateOfPreviousYearMonth = startDateOfPreviousYearMonth.withDayOfMonth(startDateOfPreviousYearMonth.lengthOfMonth());
        LocalDateTime endDateOfPreviousYearMonths = LocalDateTime.of(endDateOfPreviousYearMonth, midnight);

        try {
            if (airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_BOOKING)) {

                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingAirport(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingCountry(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingCountry(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingCountry(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingContinent(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingContinent(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingContinent(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingRegion(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingRegion(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalBookingRegion(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

                List<Object[]> moM = calculateMomYoyPercentageChange(previousMonthObjects, currentMonthObjects);
                List<Object[]> yoY = calculateMomYoyPercentageChange(previousYearSameMonthObjects, currentMonthObjects);
                buildTopDomesticInternationalResponseDTO(response, topObjects, moM , yoY);
            }

            else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_VOLUME)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeAirport(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeAirport(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeAirport(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeCountry(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeCountry(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeCountry(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeContinent(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeContinent(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeContinent(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeRegion(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeRegion(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalVolumeRegion(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }
                List<Object[]> moM = calculateMomYoyPercentageChange(previousMonthObjects, currentMonthObjects);
                List<Object[]> yoY = calculateMomYoyPercentageChange(previousYearSameMonthObjects, currentMonthObjects);
                buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_VOLUME, masterRecord.getStdVolumeUnit(), moM, yoY);

            }

            else if(airlineDashboardRequest.getTypeOfInfo().equalsIgnoreCase(AirlineDashboardConstants.INFO_TYPE_WEIGHT)) {
                AirlineHostCountryMaster masterRecord = masterService.findByCarrierCode(airlineDashboardRequest.getCarrier());
                switch (areaBy) {
                    case AirlineDashboardConstants.AREA_BY_AIRPORT -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightAirport(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightAirport(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightAirport(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightAirport(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_COUNTRY -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightCountry(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightCountry(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightCountry(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightCountry(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_CONTINENT -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightContinent(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightContinent(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightContinent(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightContinent(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    case AirlineDashboardConstants.AREA_BY_REGION -> {
                        topObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightRegion(startDate, endDate, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        currentMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightRegion(startDateOfCurrentMonths, endDateOfCurrentMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightRegion(startDateOfPreviousMonths, endDateOfPreviousMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                        previousYearSameMonthObjects = advanceFunctionAuditRepository.getTopDomesticInternationalWeightRegion(startDateOfPreviousYearMonths, endDateOfPreviousYearMonths, airlineDashboardRequest.getCarrier(), airlineDashboardRequest.getFilterValue());
                    }
                    default ->
                            throw new CpsException(AirlineDashboardConstants.INVALID_FILTER_VALUE);
                }

                List<Object[]> moM = calculateMomYoyPercentageChange(previousMonthObjects, currentMonthObjects);
                List<Object[]> yoY = calculateMomYoyPercentageChange(previousYearSameMonthObjects, currentMonthObjects);
                buildTopDomesticInternationalResponseDTO(response, topObjects, AirlineDashboardConstants.INFO_TYPE_WEIGHT, masterRecord.getStdWeightUnit(), moM, yoY);

            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new CpsException(exception.getMessage());
        }
        return response;
    }

    private void buildTopDomesticInternationalResponseDTO(DomesticInternationalResponseDTO response, List<Object[]> topObjects, String valueType, String stdUnit, List<Object[]> moM, List<Object[]> yoY) {
        List<TopDomesticInternationalResponseDTO> topDomesticInternationalResponseDTOS = new ArrayList<>();
        Float totalValue = AirlineDashboardConstants.FLOAT_ZERO;
        int index=0;
        for (Object[] array : topObjects) {
            TopDomesticInternationalResponseDTO topDomesticInternationalResponseDTO = new TopDomesticInternationalResponseDTO();
            String intl = (String)array[0];
            topDomesticInternationalResponseDTO.setIntl(Boolean.valueOf(intl));
            if(array[2] !=null) {
                Number value = (Number) array[2];
                topDomesticInternationalResponseDTO.setValue(value.floatValue());
                totalValue+=value.floatValue();
            }
            else{
                topDomesticInternationalResponseDTO.setValue(AirlineDashboardConstants.FLOAT_ZERO);
            }
            topDomesticInternationalResponseDTO.setValueType(valueType);
            topDomesticInternationalResponseDTO.setUnit(stdUnit);


            if(moM.get(index)[2] !=null){
                Number momValue = (Number) moM.get(index)[2];
                topDomesticInternationalResponseDTO.setMomData(momValue.floatValue());

            }
            if(yoY.get(index)[2] !=null){
                Number yoyValue = (Number) yoY.get(index)[2];
                topDomesticInternationalResponseDTO.setYoyData(yoyValue.floatValue());

            } index++;
            topDomesticInternationalResponseDTOS.add(topDomesticInternationalResponseDTO);
        }
        response.setTopDomesticInternationalResponseDTOList(topDomesticInternationalResponseDTOS);
        response.setTotalValue(totalValue);
    }

    private void buildTopDomesticInternationalResponseDTO(DomesticInternationalResponseDTO response, List<Object[]> topObjects, List<Object[]> moM , List<Object[]> yoY) {

        if(topObjects != null) {
            List<TopDomesticInternationalResponseDTO> topDomesticInternationalResponseDTOS = new ArrayList<>();
            Long totalValue = 0L;
            int index=0;
            for (Object[] array : topObjects) {
                TopDomesticInternationalResponseDTO topDomesticInternationalResponseDTO = new TopDomesticInternationalResponseDTO();
                String intl = (String)array[0];
                topDomesticInternationalResponseDTO.setIntl(Boolean.valueOf(intl));
                Number value = (Number) array[1];
                topDomesticInternationalResponseDTO.setValue(value.floatValue());
                topDomesticInternationalResponseDTO.setValueType(AirlineDashboardConstants.INFO_TYPE_BOOKING);
                topDomesticInternationalResponseDTO.setUnit(AirlineDashboardConstants.EMPTY_STRING);
                totalValue+=value.longValue();

                if(moM.get(index)[2] !=null){
                    Number momValue = (Number) moM.get(index)[2];
                    topDomesticInternationalResponseDTO.setMomData(momValue.floatValue());

                }
                if(yoY.get(index)[2] !=null){
                    Number yoyValue = (Number) yoY.get(index)[2];
                    topDomesticInternationalResponseDTO.setYoyData(yoyValue.floatValue());

                } index++;
                topDomesticInternationalResponseDTOS.add(topDomesticInternationalResponseDTO);

            }
            response.setTopDomesticInternationalResponseDTOList(topDomesticInternationalResponseDTOS);
            response.setTotalValue(totalValue);
        }
    }

    // calculate Mom and Yoy Percentage Change
    public List<Object[]> calculateMomYoyPercentageChange(List<Object[]> previousMonthData, List<Object[]> currentMonthData) {
        List<Object[]> momPercentageChangeList = new ArrayList<>();

        if (previousMonthData.size() != currentMonthData.size()) {
            throw new IllegalArgumentException("Input lists must have the same size");
        }

        for (int i = 0; i < previousMonthData.size(); i++) {
            Object[] previousData = previousMonthData.get(i);
            Object[] currentData = currentMonthData.get(i);
            Number previousValue = (Number) previousData[1];
            Number currentValue = (Number) currentData[1];
            double momPercentageChange = calculatePercentageChange(previousValue.doubleValue(), currentValue.doubleValue());
            momPercentageChange = Math.round(momPercentageChange * 10.0) / 10.0;
            Object[] result = new Object[]{previousValue, currentValue, momPercentageChange};
            momPercentageChangeList.add(result);
        }
        return momPercentageChangeList;
    }

    private double calculatePercentageChange(double previousValue, double currentValue) {
        if (previousValue == 0) {
            return (currentValue - previousValue) * 100.0;
        } else {
            return ((currentValue - previousValue) / Math.abs(previousValue)) * 100.0;
        }
    }

}
