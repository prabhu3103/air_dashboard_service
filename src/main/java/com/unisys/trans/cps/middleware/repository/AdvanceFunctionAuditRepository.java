package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.AdvanceFunctionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdvanceFunctionAuditRepository extends JpaRepository<AdvanceFunctionAudit, Long> {

    //Top Lanes - Total Number of Booking Count for Airport
    @Query("""
            select a.origin, a.destination, COUNT(*) AS TOPLANE
            from   AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.origin,a.destination
            order by TOPLANE desc LIMIT 5
            """)
    List<Object[]> getTopLanesBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Top Lanes - Total Number of Booking Count for Country
    @Query("""
            SELECT a.origin, a.destination, COUNT(*) AS TOPLANE
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND b.countryCode = :country
            GROUP BY a.origin, a.destination
            ORDER BY TOPLANE DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("country") String country);

    //Top Lanes - Total Number of Booking Count for Continent
    @Query("""
            SELECT a.origin, a.destination, COUNT(*) AS TOPLANE
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND b.continent = :continent
            GROUP BY a.origin, a.destination
            ORDER BY TOPLANE DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("continent") String continent);


    //Top Lanes - Total Number of Booking Count for Region
    @Query("""
            SELECT a.origin, a.destination, COUNT(*) AS TOPLANE
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            JOIN RegionMaster c ON b.continent = c.continent
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND c.regionName = :region
            GROUP BY a.origin, a.destination
            ORDER BY TOPLANE DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("region") String region);


    //Top Lanes - Total Number of Weight Count for Airport
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.origin,a.destination
            order by totalWeight desc LIMIT 5
            """)
    List<Object[]> getTopLanesWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    //Top Lanes - Total Number of Weight  for Country
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND b.countryCode = :country
            GROUP BY a.origin, a.destination
            ORDER BY totalWeight DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("country") String country);

    //Top Lanes - Total Number of Weight  for Continent
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND b.continent = :continent
            GROUP BY a.origin, a.destination
            ORDER BY totalWeight DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Lanes - Total Number of Weight  for Region
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            JOIN RegionMaster c ON b.continent = c.continent
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND c.regionName = :region
            GROUP BY a.origin, a.destination
            ORDER BY totalWeight DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                           @Param("carrier") String carrier, @Param("region") String region);


    //Top Lanes - Total Number of Volume  for Airport
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.origin,a.destination
            order by totalVolume desc LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Top Lanes - Total Number of Volume  for Country
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND b.countryCode = :country
            GROUP BY a.origin, a.destination
            ORDER BY totalVolume DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("country") String country);

    //Top Lanes - Total Number of Volume  for Continent
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND b.continent = :continent
            GROUP BY a.origin, a.destination
            ORDER BY totalVolume DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Lanes - Total Number of Volume  for Region
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            FROM AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            JOIN RegionMaster c ON b.continent = c.continent
            WHERE a.eventDate >= :startDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            AND a.eventDate <= :endDate
            AND a.carrier = :carrier
            AND c.regionName = :region
            GROUP BY a.origin, a.destination
            ORDER BY totalVolume DESC
            LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                           @Param("carrier") String carrier, @Param("region") String region);


    //Top Agents - Total Number of Booking Count for AirPort
    @Query("""
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :origin
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Booking Count for Country
    @Query("""
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a, CityCountryMaster b
            where a.origin = b.code
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.countryCode = :country
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("country") String country);


    //Top Agents - Total Number of Booking Count for Continent
    @Query("""
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a, CityCountryMaster b
            where a.origin = b.code
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.continent = :continent
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Agents - Total Number of Booking Count for Region
    @Query("""
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
            where a.origin = b.code and b.continent = c.continent
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and c.regionName = :region
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("region") String region);

    //Top Agents - Total Number of Volume for AirPort
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdVol) as totalNoOfVolumeCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :origin
            group by  a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Volume for Country
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdVol)  as totalNoOfVolumeCount from AdvanceFunctionAudit a, CityCountryMaster b
            where a.origin = b.code
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.countryCode = :country
            group by a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("country") String country);


    //Top Agents - Total Number of Volume for Continent
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdVol) as totalNoOfVolumeCount from AdvanceFunctionAudit a, CityCountryMaster b
            where a.origin = b.code
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.continent = :continent
            group by a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Agents - Total Number of Volume for Region
    @Query("""
            select  a.carrier, a.accNo, SUM(a.stdVol) as totalNoOfVolumeCount from AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
            where a.origin = b.code and b.continent = c.continent
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and c.regionName = :region
            group by a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("region") String region);


    //Top Agents - Total Number of Weight for AirPort
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :origin
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Weight for Country
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a, CityCountryMaster b
            where a.origin = b.code
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.countryCode = :country
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("country") String country);


    //Top Agents - Total Number of Weight for Continent
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a, CityCountryMaster b
            where a.origin = b.code
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.continent = :continent
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Agents - Total Number of Weight for Region
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
            where a.origin = b.code and b.continent = c.continent
            and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and c.regionName = :region
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("region") String region);

    //Point Of Sales - Total Number of Booking Count for Airport
    @Query(value = """
            select s.day, s.bookingCount, 
            case 
                         when pm.pmbookingCount <> 0 then round(((m.monthbookingCount - pm.pmbookingCount) * 100 / pm.pmbookingCount), 1) 
                         when pm.pmbookingCount = 0  and m.monthbookingCount = 0 then 0 
                         when pm.pmbookingCount = 0  then 100 
            end as momPercent,
            case 
                         when py.pybookingCount <> 0 then round(((y.yearbookingCount - py.pybookingCount) * 100 / py.pybookingCount), 1) 
                         when py.pybookingCount = 0  and y.yearbookingCount = 0 then 0 
                         when py.pybookingCount = 0  then 100 
            end as yoyPercent 
            from 
                     (select a.eventDate as day, count(*) as bookingCount 		    
            	from AdvanceFunctionAudit a 
            	where a.eventDate >= :startDate and a.eventDate <= :endDate 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport 
            	group by a.eventDate) s, 
                     (select count(*) as monthbookingCount 			    
            	from AdvanceFunctionAudit a 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) m, 
                     (select count(*) as pmbookingCount 			    
            	from AdvanceFunctionAudit a				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) pm, 
                     (select count(*) as yearbookingCount 			    
            	from AdvanceFunctionAudit a 
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) y, 
                     (select count(*) as pybookingCount			    
            	from AdvanceFunctionAudit a 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Point Of Sales - Total Number of Booking Count for Country
    @Query(value = """
            select s.day, s.bookingCount, 
            case 
                when pm.pmbookingCount <> 0 then round(((m.monthbookingCount - pm.pmbookingCount) * 100 / pm.pmbookingCount), 1) 
                when pm.pmbookingCount = 0  and m.monthbookingCount = 0 then 0 
                when pm.pmbookingCount = 0  then 100 
            end as momPercent,
            case 
                when py.pybookingCount <> 0 then round(((y.yearbookingCount - py.pybookingCount) * 100 / py.pybookingCount), 1) 
                when py.pybookingCount = 0  and y.yearbookingCount = 0 then 0 
                when py.pybookingCount = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, count(*) as bookingCount 
				from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				where a.eventDate >= :startDate and a.eventDate <= :endDate 
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S' 
				and a.carrier = :carrier
				and b.countryCode = :country 
				group by a.eventDate) s, 
            (select count(*) as monthbookingCount 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) m, 
            (select count(*) as pmbookingCount 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) pm, 
            (select count(*) as yearbookingCount 			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 	
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) y, 
            (select count(*) as pybookingCount			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 	
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("country") String country);

    //Point Of Sales - Total Number of Booking Count for Continent
    @Query(value = """
            select s.day, s.bookingCount, 
            case 
                when pm.pmbookingCount <> 0 then round(((m.monthbookingCount - pm.pmbookingCount) * 100 / pm.pmbookingCount), 1) 
                when pm.pmbookingCount = 0  and m.monthbookingCount = 0 then 0 
                when pm.pmbookingCount = 0  then 100 
            end as momPercent,
            case 
                when py.pybookingCount <> 0 then round(((y.yearbookingCount - py.pybookingCount) * 100 / py.pybookingCount), 1) 
                when py.pybookingCount = 0  and y.yearbookingCount = 0 then 0 
                when py.pybookingCount = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, count(*) as bookingCount
				from AdvanceFunctionAudit a
				join CityCountryMaster b on a.org = b.code
				where a.eventDate >= :startDate and a.eventDate <= :endDate
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
				and a.carrier = :carrier
				and b.continent = :continent 
				group by a.eventDate) s, 
            (select count(*) as monthbookingCount 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) m, 
            (select count(*) as pmbookingCount 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) pm, 
            (select count(*) as yearbookingCount 			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) y, 
            (select count(*) as pybookingCount			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                   @Param("carrier") String carrier, @Param("continent") String continent);


    //Point Of Sales - Total Number of Booking Count for Region
    @Query(value = """
            select s.day, s.bookingCount, 
            case 
                when pm.pmbookingCount <> 0 then round(((m.monthbookingCount - pm.pmbookingCount) * 100 / pm.pmbookingCount), 1) 
                when pm.pmbookingCount = 0  and m.monthbookingCount = 0 then 0 
                when pm.pmbookingCount = 0  then 100 
            end as momPercent,
            case 
                when py.pybookingCount <> 0 then round(((y.yearbookingCount - py.pybookingCount) * 100 / py.pybookingCount), 1) 
                when py.pybookingCount = 0  and y.yearbookingCount = 0 then 0 
                when py.pybookingCount = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, count(*) as bookingCount
				from AdvanceFunctionAudit a
				join CityCountryMaster b on a.org = b.code
				join RegionMaster c on b.continent = c.continent
				where a.eventDate >= :startDate and a.eventDate <= :endDate
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
				and a.carrier = :carrier
				and c.regionName = :region 
				group by a.eventDate) s, 
            (select count(*) as monthbookingCount 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) m, 
            (select count(*) as pmbookingCount 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 			
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) pm, 
            (select count(*) as yearbookingCount 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) y, 
            (select count(*) as pybookingCount			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("region") String region);


    //Point Of Sales - Total Number of Weight Count for Airport
    @Query(value = """
            select s.day, s.weight, 
            case 
                         when pm.pmweight <> 0 then round(((m.monthweight - pm.pmweight) * 100 / pm.pmweight), 1) 
                         when pm.pmweight = 0  and m.monthweight = 0 then 0 
                         when pm.pmweight = 0  then 100 
            end as momPercent,
            case 
                         when py.pyweight <> 0 then round(((y.yearweight - py.pyweight) * 100 / py.pyweight), 1) 
                         when py.pyweight = 0  and y.yearweight = 0 then 0 
                         when py.pyweight = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdWeight) as weight 		    
            	from AdvanceFunctionAudit a 
            	where a.eventDate >= :startDate and a.eventDate <= :endDate 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport 
            	group by a.eventDate) s, 
            (select sum(a.stdWeight) as monthweight 			    
            	from AdvanceFunctionAudit a 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) m, 
            (select sum(a.stdWeight) as pmweight 			    
            	from AdvanceFunctionAudit a				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) pm, 
            (select sum(a.stdWeight) as yearweight 			    
            	from AdvanceFunctionAudit a 
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) y, 
            (select sum(a.stdWeight) as pyweight			    
            	from AdvanceFunctionAudit a 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    //Point Of Sales - Total Number of Weight  for Country
    @Query(value = """
            select s.day, s.weight, 
            case 
                when pm.pmweight <> 0 then round(((m.monthweight - pm.pmweight) * 100 / pm.pmweight), 1) 
                when pm.pmweight = 0  and m.monthweight = 0 then 0 
                when pm.pmweight = 0  then 100 
            end as momPercent,
            case 
                when py.pyweight <> 0 then round(((y.yearweight - py.pyweight) * 100 / py.pyweight), 1) 
                when py.pyweight = 0  and y.yearweight = 0 then 0 
                when py.pyweight = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdWeight) as weight  
				from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				where a.eventDate >= :startDate and a.eventDate <= :endDate 
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S' 
				and a.carrier = :carrier
				and b.countryCode = :country 
				group by a.eventDate) s, 
            (select sum(a.stdWeight) as monthweight 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) m, 
            (select sum(a.stdWeight) as pmweight 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) pm, 
            (select sum(a.stdWeight) as yearweight 			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 	
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) y, 
            (select sum(a.stdWeight) as pyweight			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 	
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("country") String country);

    //Point Of Sales - Total Number of Weight for Continent
    @Query(value = """
            select s.day, s.weight, 
            case 
                when pm.pmweight <> 0 then round(((m.monthweight - pm.pmweight) * 100 / pm.pmweight), 1) 
                when pm.pmweight = 0  and m.monthweight = 0 then 0 
                when pm.pmweight = 0  then 100 
            end as momPercent,
            case 
                when py.pyweight <> 0 then round(((y.yearweight - py.pyweight) * 100 / py.pyweight), 1) 
                when py.pyweight = 0  and y.yearweight = 0 then 0 
                when py.pyweight = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdWeight) as weight
				from AdvanceFunctionAudit a
				join CityCountryMaster b on a.org = b.code
				where a.eventDate >= :startDate and a.eventDate <= :endDate
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
				and a.carrier = :carrier
				and b.continent = :continent 
				group by a.eventDate) s, 
            (select sum(a.stdWeight) as monthweight 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) m, 
            (select sum(a.stdWeight) as pmweight 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) pm, 
            (select sum(a.stdWeight) as yearweight 			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) y, 
            (select sum(a.stdWeight) as pyweight			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                  @Param("carrier") String carrier, @Param("continent") String continent);

    //Point Of Sales - Total Number of Weight  for Region
    @Query(value = """
            select s.day, s.weight, 
            case 
                when pm.pmweight <> 0 then round(((m.monthweight - pm.pmweight) * 100 / pm.pmweight), 1) 
                when pm.pmweight = 0  and m.monthweight = 0 then 0 
                when pm.pmweight = 0  then 100 
            end as momPercent,
            case 
                when py.pyweight <> 0 then round(((y.yearweight - py.pyweight) * 100 / py.pyweight), 1) 
                when py.pyweight = 0  and y.yearweight = 0 then 0 
                when py.pyweight = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdWeight) as weight
				from AdvanceFunctionAudit a
				join CityCountryMaster b on a.org = b.code
				join RegionMaster c on b.continent = c.continent
				where a.eventDate >= :startDate and a.eventDate <= :endDate
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
				and a.carrier = :carrier
				and c.regionName = :region 
				group by a.eventDate) s, 
            (select sum(a.stdWeight) as monthweight 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) m, 
            (select sum(a.stdWeight) as pmweight 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 			
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) pm, 
            (select sum(a.stdWeight) as yearweight 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) y, 
            (select sum(a.stdWeight) as pyweight			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("region") String region);


    //Point Of Sales - Total Number of Volume  for Airport
    @Query(value = """
            select s.day, s.volume, 
            case 
                         when pm.pmvolume <> 0 then round(((m.monthvolume - pm.pmvolume) * 100 / pm.pmvolume), 1) 
                         when pm.pmvolume = 0  and m.monthvolume = 0 then 0 
                         when pm.pmvolume = 0  then 100 
            end as momPercent,
            case 
                         when py.pyvolume <> 0 then round(((y.yearvolume - py.pyvolume) * 100 / py.pyvolume), 1) 
                         when py.pyvolume = 0  and y.yearvolume = 0 then 0 
                         when py.pyvolume = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdVolume) as volume 		    
            	from AdvanceFunctionAudit a 
            	where a.eventDate >= :startDate and a.eventDate <= :endDate 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport 
            	group by a.eventDate) s, 
            (select sum(a.stdVolume) as monthvolume 			    
            	from AdvanceFunctionAudit a 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) m, 
            (select sum(a.stdVolume) as pmvolume 			    
            	from AdvanceFunctionAudit a				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) pm, 
            (select sum(a.stdVolume) as yearvolume 			    
            	from AdvanceFunctionAudit a 
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) y, 
            (select sum(a.stdVolume) as pyvolume			    
            	from AdvanceFunctionAudit a 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and a.org = :originAirport) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Point Of Sales - Total Number of Volume  for Country
    @Query(value = """
            select s.day, s.volume, 
            case 
                when pm.pmvolume <> 0 then round(((m.monthvolume - pm.pmvolume) * 100 / pm.pmvolume), 1) 
                when pm.pmvolume = 0  and m.monthvolume = 0 then 0 
                when pm.pmvolume = 0  then 100 
            end as momPercent,
            case 
                when py.pyvolume <> 0 then round(((y.yearvolume - py.pyvolume) * 100 / py.pyvolume), 1) 
                when py.pyvolume = 0  and y.yearvolume = 0 then 0 
                when py.pyvolume = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdVolume) as volume  
				from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				where a.eventDate >= :startDate and a.eventDate <= :endDate 
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S' 
				and a.carrier = :carrier
				and b.countryCode = :country 
				group by a.eventDate) s, 
            (select sum(a.stdVolume) as monthvolume 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) m, 
            (select sum(a.stdVolume) as pmvolume 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) pm, 
            (select sum(a.stdVolume) as yearvolume 			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 	
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) y, 
            (select sum(a.stdVolume) as pyvolume			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 	
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.countryCode = :country) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("country") String country);

    //Point Of Sales - Total Number of Volume  for Continent
    @Query(value = """
            select s.day, s.volume, 
            case 
                when pm.pmvolume <> 0 then round(((m.monthvolume - pm.pmvolume) * 100 / pm.pmvolume), 1) 
                when pm.pmvolume = 0  and m.monthvolume = 0 then 0 
                when pm.pmvolume = 0  then 100 
            end as momPercent,
            case 
                when py.pyvolume <> 0 then round(((y.yearvolume - py.pyvolume) * 100 / py.pyvolume), 1) 
                when py.pyvolume = 0  and y.yearvolume = 0 then 0 
                when py.pyvolume = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdVolume) as volume
				from AdvanceFunctionAudit a
				join CityCountryMaster b on a.org = b.code
				where a.eventDate >= :startDate and a.eventDate <= :endDate
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
				and a.carrier = :carrier
				and b.continent = :continent 
				group by a.eventDate) s, 
            (select sum(a.stdVolume) as monthvolume 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) m, 
            (select sum(a.stdVolume) as pmvolume 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 				
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) pm, 
            (select sum(a.stdVolume) as yearvolume 			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) y, 
            (select sum(a.stdVolume) as pyvolume			    
            	from AdvanceFunctionAudit a 
            	join CityCountryMaster b on a.org = b.code 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and b.continent = :continent) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                  @Param("carrier") String carrier, @Param("continent") String continent);

    //Point Of Sales - Total Number of Volume  for Region
    @Query(value = """
            select s.day, s.volume, 
            case 
                when pm.pmvolume <> 0 then round(((m.monthvolume - pm.pmvolume) * 100 / pm.pmvolume), 1) 
                when pm.pmvolume = 0  and m.monthvolume = 0 then 0 
                when pm.pmvolume = 0  then 100 
            end as momPercent,
            case 
                when py.pyvolume <> 0 then round(((y.yearvolume - py.pyvolume) * 100 / py.pyvolume), 1) 
                when py.pyvolume = 0  and y.yearvolume = 0 then 0 
                when py.pyvolume = 0  then 100 
            end as yoyPercent 
            from 
            (select a.eventDate as day, sum(a.stdVolume) as volume
				from AdvanceFunctionAudit a
				join CityCountryMaster b on a.org = b.code
				join RegionMaster c on b.continent = c.continent
				where a.eventDate >= :startDate and a.eventDate <= :endDate
				and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
				and a.carrier = :carrier
				and c.regionName = :region 
				group by a.eventDate) s, 
            (select sum(a.stdVolume) as monthvolume 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 
            	where month(a.eventDate)=month(getdate()) and year(a.eventDate)=year(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) m, 
            (select sum(a.stdVolume) as pmvolume 			    
            	from AdvanceFunctionAudit a	
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 			
            	where 
                (
                 month(getdate()) <> 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=year(getdate()) 
                 or 
                 month(getdate()) = 1 and month(a.eventDate)=(month(getdate())-1)  and  year(a.eventDate)=(year(getdate())-1) 
                ) 			
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) pm, 
            (select sum(a.stdVolume) as yearvolume 			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent
            	where year(a.eventDate)=year(getdate()) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) y, 
            (select sum(a.stdVolume) as pyvolume			    
            	from AdvanceFunctionAudit a 
				join CityCountryMaster b on a.org = b.code 
				join RegionMaster c on b.continent = c.continent 
            	where year(a.eventDate)=(year(getdate())-1) and month(a.eventDate)=month(getdate()) 
            	and a.txnStatus != 'E' and a.txnStatus != '' and a.status = 'S' 
            	and a.carrier = :carrier 
            	and c.regionName = :region) py 
                     """, nativeQuery = true)
    List<Object[]> getPointOfSalesVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("region") String region);


    //Top Commodity - Total Number of Booking Count for Airport
    @Query("""
           select a.commodity, COUNT(a.commodity) AS COMMODITY_COUNT
           from AdvanceFunctionAudit a
           where a.eventDate >= :startDate and a.eventDate <= :endDate
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier = :carrier
           and a.origin = :originAirport
           group by a.commodity
           order by COMMODITY_COUNT desc
           LIMIT 6
           """)
    List<Object[]> getTopCommodityBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    //Top Commodity - Total Number of Booking Count for Country
    @Query("""
            select
            a.commodity, COUNT(a.commodity) AS COMMODITY_COUNT
            from AdvanceFunctionAudit a
            join CityCountryMaster b ON a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND b.countryCode = :country
            group by a.commodity
            order by COMMODITY_COUNT desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("country") String country);


    //Top Commodity - Total Number of Booking Count for Continent
    @Query("""
            select
            a.commodity, COUNT(a.commodity) AS COMMODITY_COUNT
            from  AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND b.continent = :continent
            group by a.commodity
            order by COMMODITY_COUNT desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                   @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Commodity - Total Number of Booking Count for Region
    @Query("""
            select
            a.commodity, COUNT(a.commodity) AS COMMODITY_COUNT
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            JOIN RegionMaster r ON b.continent = r.continent
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND r.regionName = :region
            group by a.commodity
            order by COMMODITY_COUNT desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("region") String region);

    //Top Commodity - Total Number of Weight Count for Airport
    @Query("""
            select
            a.commodity, SUM(a.stdWeight) AS totalWeight
            from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.commodity
            order by totalWeight desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    //Top Commodity - Total Number of Weight Count for Country
    @Query("""
            select
            a.commodity, SUM(a.stdWeight) AS totalWeight
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND b.countryCode = :country
            group by a.commodity
            order by totalWeight desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("country") String country);


    //Top Commodity - Total Number of Weight Count for Continent
    @Query("""
            select
            a.commodity, SUM(a.stdWeight) AS totalWeight
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND b.continent = :continent
            group by a.commodity
            order by totalWeight desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                  @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Commodity - Total Number of Weight Count for Region
    @Query("""
            select
            a.commodity, SUM(a.stdWeight) AS totalWeight
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            JOIN RegionMaster r ON b.continent = r.continent
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND r.regionName = :region
            group by a.commodity
            order by totalWeight desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("region") String region);


    //Top Commodity - Total Number of Volume Count for Airport
    @Query("""
            select
            a.commodity, SUM(a.stdVol) AS totalVolume
            from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.commodity
            order by totalVolume desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Top Commodity - Total Number of volume Count for Country
    @Query("""
            select
            a.commodity, SUM(a.stdVol) AS totalVolume
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND b.countryCode = :country
            group by a.commodity
            order by totalVolume desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("country") String country);

    //Top Commodity - Total Number of volume Count for Continent
    @Query("""
            select
            a.commodity, SUM(a.stdVol) AS totalVolume
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND b.continent = :continent
            group by a.commodity
            order by totalVolume desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                  @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Commodity - Total Number of volume Count for Region
    @Query("""
            select
            a.commodity, SUM(a.stdVol) AS totalVolume
            from AdvanceFunctionAudit a
            JOIN CityCountryMaster b ON a.origin = b.code
            JOIN RegionMaster r ON b.continent = r.continent
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            AND r.regionName = :region
            group by a.commodity
            order by totalVolume desc
            LIMIT 6
            """)
    List<Object[]> getTopCommodityVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("region") String region);


    @Query(value = """
           select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),2) as MOMPercent,
           ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
           (select a.productCode,p.description, COUNT(*) AS TOPPRODUCTS
           from   AdvanceFunctionAudit a , ProductType p
           where
           a.eventDate >= :startDate and a.eventDate <= :endDate
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier=p.airline
           and a.productCode=p.productType
           and a.org=:originAirport
           and a.carrier=:carrier
           group by a.productCode,p.description
           ) s left join
           (select a.productCode, COUNT(*) AS TOPPRODUCTS
           from   AdvanceFunctionAudit a, ProductType p
           where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier=p.airline
           and a.productCode=p.productType
           and a.org=:originAirport
           and a.carrier=:carrier
           group by a.productCode
           ) m
           on s.productCode = m.productCode left join
           (select a.productCode, COUNT(*) AS TOPPRODUCTS
           from   AdvanceFunctionAudit a, ProductType p
           where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier=p.airline
           and a.productCode=p.productType
           and a.org=:originAirport
           and a.carrier=:carrier
           group by a.productCode
           ) y
           on s.productCode = y.productCode
           order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
           """, nativeQuery = true)
    List<Object[]> getTopProductsBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("carrier") String carrier,@Param("originAirport") String originAirport);

    @Query(value = """
            select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),2) as MOMPercent,
            ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
            (select a.productCode,p.description, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, ProductType p, CityCountryMaster b
            where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a,  CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("carrier") String carrier,@Param("country") String country);

    @Query(value = """
            select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),0.00,2) as MOMPercent,
            ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
            (select a.productCode,p.description, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a , ProductType p,  CityCountryMaster b
            where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.org = b.code
            and b.continent = :continent
            and a.carrier=:carrier
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a,  CityCountryMaster b,  ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, CityCountryMaster b,  ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("carrier") String carrier,@Param("continent") String continent);


    @Query(value = """
            select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),2) as MOMPercent,
            ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
            (select a.productCode ,p.description, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, ProductType p, CityCountryMaster b, RegionMaster c
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            and a.carrier=:carrier
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate ,@Param("carrier") String carrier, @Param("region") String region);


    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,ProductType p
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier,@Param("originAirport") String originAirport);


    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a ,ProductType p, CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("country") String country);


    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a ,ProductType p,CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("continent") String continent);




    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a ,ProductType p, CityCountryMaster b, RegionMaster c where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("region") String region);


    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,ProductType p
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier,@Param("originAirport") String originAirport);


    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a ,ProductType p, CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """,nativeQuery = true)
    List<Object[]> getTopProductsVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("country") String country);



    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a ,ProductType p,CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode,p.description) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("continent") String continent);

    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a ,ProductType p,CityCountryMaster b, RegionMaster c where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.productType
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """,nativeQuery = true)
    List<Object[]> getTopProductsVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("region") String region);

    //Top Domestic and International - Total Number of Booking Count for AirPort
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )

           SELECT
           ac.category,
           COALESCE(COUNT(CategoryCTE.category), 0) AS category_count
           FROM AllCategories ac
           LEFT JOIN (
           SELECT
           a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER,
           CASE
           WHEN a.ORG NOT IN (
           SELECT b.CODE
           FROM CITYCOUNTRYMASTER b
           JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
           WHERE c.CARRIERCODE = :carrier
           ) OR a.DEST NOT IN (
           SELECT b.CODE
           FROM CITYCOUNTRYMASTER b
           JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
           WHERE c.CARRIERCODE = :carrier
           ) THEN 'true'
           ELSE 'false'
           END AS category
           FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           AND a.CARRIER = :carrier
           AND a.ORG = :origin
           ) AS CategoryCTE
           ON ac.category = CategoryCTE.category
           GROUP BY ac.category
           ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Domestic and International - Total Number of Booking Count for Country
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b WHERE a.ORG = b.CODE
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and b.COUNTRYCODE = :country
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                             @Param("carrier") String carrier, @Param("country") String country);


    //Top Domestic and International - Total Number of Booking Count for Continent
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
                                       
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b WHERE a.ORG = b.CODE
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and b.CONTINENT=:continent
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                               @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Domestic and International - Total Number of Booking Count for Region
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
                                        
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b, REGIONMASTER e WHERE
            a.ORG = b.CODE and b.CONTINENT = e.CONTINENT
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and e.REGIONNAME= :region
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("region") String region);

    //Top Domestic and International - Total Number of Volume for AirPort
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
                                       
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDVOLUME) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDVOLUME,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and a.ORG = :origin
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Domestic and International - Total Number of Volume for Country
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
           
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDVOLUME) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDVOLUME,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b  WHERE a.ORG = b.CODE
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and b.COUNTRYCODE = :country
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("country") String country);


    //Top Domestic and International - Total Number of Volume for Continent
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDVOLUME) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDVOLUME,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b WHERE a.ORG = b.CODE
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and b.CONTINENT=:continent
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                              @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Domestic and International - Total Number of Volume for Region
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDVOLUME) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDVOLUME,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b, REGIONMASTER e WHERE a.ORG = b.CODE and b.CONTINENT = e.CONTINENT
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and e.REGIONNAME= :region
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                           @Param("carrier") String carrier, @Param("region") String region);

    //Top Domestic and International - Total Number of Weight for AirPort
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDWEIGHT) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDWEIGHT,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and a.ORG = :origin
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Domestic and International - Total Number of Weight for Country
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDWEIGHT) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDWEIGHT,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b WHERE a.ORG = b.CODE
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and b.COUNTRYCODE = :country
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("country") String country);

    //Top Domestic and International - Total Number of Weight for Continent
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDWEIGHT) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDWEIGHT,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a , CITYCOUNTRYMASTER b WHERE a.ORG = b.CODE
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and b.CONTINENT=:continent
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                              @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Domestic and International - Total Number of Weight for Region
    @Query(value ="""
            WITH AllCategories AS (
            SELECT 'false' AS category
            UNION ALL
            SELECT 'true'
            )
            
            SELECT
            ac.category,
            COALESCE(COUNT(CategoryCTE.category), 0) AS category_count, SUM(CategoryCTE.STDWEIGHT) AS totalValue
            FROM AllCategories ac
            LEFT JOIN (
            SELECT
            a.AWBNUMBER, a.ORG, a.EVENTDATE, a.CONFNUMBER, a.CARRIER, a.STDWEIGHT,
            CASE
            WHEN a.ORG NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) OR a.DEST NOT IN (
            SELECT b.CODE
            FROM CITYCOUNTRYMASTER b
            JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
            WHERE c.CARRIERCODE = :carrier
            ) THEN 'true'
            ELSE 'false'
            END AS category
            FROM ADVANCEFUNCTIONAUDIT a, CITYCOUNTRYMASTER b, REGIONMASTER e WHERE a.ORG = b.CODE
            and b.CONTINENT = e.CONTINENT
            and a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.CARRIER = :carrier
            and e.REGIONNAME= :region
            ) AS CategoryCTE
            ON ac.category = CategoryCTE.category
            GROUP BY ac.category
            ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                           @Param("carrier") String carrier, @Param("region") String region);


    @Query("""
            select count(*) from BranchAccount a  where MONTH(a.actionDate)=MONTH(:currentDate)
            and YEAR(a.actionDate)=YEAR(:currentDate)""")

    Object[] getNewAgentsInCurrentMonth(@Param("currentDate") LocalDateTime currentDate);


    @Query(value = """
           select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),2) as MOMPercent,
           ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
           (select a.productCode,p.description, COUNT(*) AS TOPPRODUCTS
           from   AdvanceFunctionAudit a , ProductType p
           where
           a.eventDate >= :startDate and a.eventDate <= :endDate
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier=p.airline
           and a.productCode=p.description
           and a.org=:originAirport
           and a.carrier=:carrier
           group by a.productCode,p.description
           ) s left join
           (select a.productCode, COUNT(*) AS TOPPRODUCTS
           from   AdvanceFunctionAudit a, ProductType p
           where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier=p.airline
           and a.productCode=p.description
           and a.org=:originAirport
           and a.carrier=:carrier
           group by a.productCode
           ) m
           on s.productCode = m.productCode left join
           (select a.productCode, COUNT(*) AS TOPPRODUCTS
           from   AdvanceFunctionAudit a, ProductType p
           where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
           and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
           and a.carrier=p.airline
           and a.productCode=p.description
           and a.org=:originAirport
           and a.carrier=:carrier
           group by a.productCode
           ) y
           on s.productCode = y.productCode
           order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
           """, nativeQuery = true)
    List<Object[]> getTopProductsBookingAirportForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("carrier") String carrier,@Param("originAirport") String originAirport);

    @Query(value = """
            select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),2) as MOMPercent,
            ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
            (select a.productCode,p.description, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, ProductType p, CityCountryMaster b
            where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a,  CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsBookingCountryForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("carrier") String carrier,@Param("country") String country);

    @Query(value = """
            select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),0.00,2) as MOMPercent,
            ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
            (select a.productCode,p.description, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a , ProductType p,  CityCountryMaster b
            where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.org = b.code
            and b.continent = :continent
            and a.carrier=:carrier
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a,  CityCountryMaster b,  ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, CityCountryMaster b,  ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsBookingContinentForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("carrier") String carrier,@Param("continent") String continent);


    @Query(value = """
            select s.productCode,s.description,s.TOPPRODUCTS, ROUND(((s.TOPPRODUCTS - m.TOPPRODUCTS)*100/ m.TOPPRODUCTS),2) as MOMPercent,
            ROUND(((s.TOPPRODUCTS - y.TOPPRODUCTS)*100/ y.TOPPRODUCTS),2) as YOYPercent from
            (select a.productCode ,p.description, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, ProductType p, CityCountryMaster b, RegionMaster c
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            and a.carrier=:carrier
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, COUNT(*) AS TOPPRODUCTS
            from   AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.TOPPRODUCTS desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsBookingRegionForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate ,@Param("carrier") String carrier, @Param("region") String region);


    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,ProductType p
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightAirportForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier,@Param("originAirport") String originAirport);


    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a ,ProductType p, CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightCountryForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("country") String country);


    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a ,ProductType p,CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightContinentForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("continent") String continent);




    @Query(value = """
            select s.productCode,s.description,s.totalWeight, ROUND(((s.totalWeight - m.totalWeight)*100/ m.totalWeight),2) as MOMPercent,
            ROUND(((s.totalWeight - y.totalWeight)*100/ y.totalWeight),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a ,ProductType p, CityCountryMaster b, RegionMaster c where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdWeight) AS totalWeight
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalWeight desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsWeightRegionForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("region") String region);


    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,ProductType p
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier=:carrier
            and a.org=:originAirport
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsVolumeAirportForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier,@Param("originAirport") String originAirport);


    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a ,ProductType p, CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.countryCode = :country
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """,nativeQuery = true)
    List<Object[]> getTopProductsVolumeCountryForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("country") String country);



    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a ,ProductType p,CityCountryMaster b where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode,p.description) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a, CityCountryMaster b, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = :continent
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """, nativeQuery = true)
    List<Object[]> getTopProductsVolumeContinentForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("continent") String continent);

    @Query(value = """
            select s.productCode,s.description,s.totalVolume, ROUND(((s.totalVolume - m.totalVolume)*100/ m.totalVolume),2) as MOMPercent,
            ROUND(((s.totalVolume - y.totalVolume)*100/ y.totalVolume),2) as YOYPercent from
            (select a.productCode ,p.description, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a ,ProductType p,CityCountryMaster b, RegionMaster c where
            a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode,p.description
            ) s left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c, ProductType p
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier=p.airline
            and a.productCode=p.description
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) y
            on s.productCode = y.productCode
            order by s.totalVolume desc offset 0 rows fetch next 5 rows only
            """,nativeQuery = true)
    List<Object[]> getTopProductsVolumeRegionForAA(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("region") String region);

}
