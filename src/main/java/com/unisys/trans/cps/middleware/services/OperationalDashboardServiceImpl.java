package com.unisys.trans.cps.middleware.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.response.BranchActivity;
import com.unisys.trans.cps.middleware.models.response.CarrierBookings;
import com.unisys.trans.cps.middleware.models.response.FunctionAuditDB;
import com.unisys.trans.cps.middleware.repository.OperationalDashboardRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class OperationalDashboardServiceImpl implements OperationalDashboardService {

	private final OperationalDashboardRepository opDashboardRepository;

	public OperationalDashboardServiceImpl(OperationalDashboardRepository opDashboardRepository) {
		this.opDashboardRepository = opDashboardRepository;
	}
    
	/**
	 * This method takes data from FunctionAudit DB and calculate list of carriers with
	 * its booking count and percentage 
	 * 
	 * @param branchId
	 * @return List<BranchActivity>
	 */
    @Override
    @Cacheable(value = "AllCarrierCode")
    public List<BranchActivity> getAllBranchBookings(int branchId) {
        log.info("Inside getAllBranchBookings method under OperationalDashboardService");
        List<BranchActivity> branchActivities = new ArrayList<>();
        try {
        //Get All dates in 14 days Interval
		
		List<LocalDate> listOfDates = Stream.iterate(LocalDate.now(), date -> date.minusDays(1)).limit(14).toList();
		 
        //Get Count of Bookings for each carrier in the interval from FunctionAudit table
        List<FunctionAuditDB> functionAuditDBData = opDashboardRepository.getAllBranchBookings(branchId);
            

        //Traverse 14 days interval, If data available in DB , add to the response else keep it empty
          Map<String, List<FunctionAuditDB>> dBDataperDate = functionAuditDBData.stream()
          		  .collect(Collectors.groupingBy(FunctionAuditDB::getEventDate));
			listOfDates.stream().forEach(date -> {
				BranchActivity branchActivity = new BranchActivity();
				branchActivity.setEventDate(date.toString());
				List<CarrierBookings> carrierBookingsList = new ArrayList<>();
				for (Entry<String, List<FunctionAuditDB>> entry : dBDataperDate.entrySet()) {
					if (date.toString().equals(entry.getKey())) {
						carrierBookingsList = entry.getValue().stream().map(dbDataperDateRow -> {
							CarrierBookings carrierBookings = new CarrierBookings(dbDataperDateRow.getCarrier(),
									dbDataperDateRow.getNumberOfBookings());
							return carrierBookings;
						}).toList();
					}
					
					//count total bookings of each carrier and calculate percentage for each carrier
						Integer totalNumberOfBookings = carrierBookingsList.stream()
								.mapToInt(carrierBooking -> Integer.parseInt(carrierBooking.getNumberOfBookings()))
								 .sum();
						carrierBookingsList.stream()
								.forEach(carrierBooking -> 
									carrierBooking.setPercentage((float)(Integer.parseInt(carrierBooking.getNumberOfBookings())*100) / totalNumberOfBookings));
						branchActivity.setTotalBooking(String.valueOf(totalNumberOfBookings));
						branchActivity.setCarrierWiseBookings(carrierBookingsList);
					}
				
				
        	  branchActivities.add(branchActivity);
        	         	  
          });
          log.info("branchActivities::::::::"+branchActivities);
        }
        catch(ArithmeticException arithmeticException) {
			throw arithmeticException;
        } catch (CpsException cpsException) {
		throw cpsException;
        }
        return branchActivities;
    }
}
