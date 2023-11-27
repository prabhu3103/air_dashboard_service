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
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select c.code from CityCountryMaster c where c.countryCode=:origin)
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Booking Count for Continent
    @Query("""
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select c.code from CityCountryMaster c where c.continent=:origin)
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Booking Count for Region
    @Query("""
            select a.carrier, a.accNo, COUNT(*) as totalNoOfBookingCount from AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
            where a.origin = b.code and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where
            b.continent in(select c.continent from RegionMaster c where c.regionName= :origin))
            group by a.carrier, a.accNo order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);

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
            select a.carrier, a.accNo, SUM(a.stdVol)  as totalNoOfVolumeCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select c.code from CityCountryMaster c where c.countryCode=:origin)
            group by a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Volume for Continent
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdVol) as totalNoOfVolumeCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select c.code from CityCountryMaster c where c.continent=:origin)
            group by a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Volume for Region
    @Query("""
            select  a.carrier, a.accNo, SUM(a.stdVol) as totalNoOfVolumeCount from AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
            where a.origin = b.code and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where
            b.continent in(select c.continent from RegionMaster c where c.regionName= :origin))
            group by a.carrier, a.accNo order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("origin") String origin);


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
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select c.code from CityCountryMaster c where c.countryCode=:origin)
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Weight for Continent
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select c.code from CityCountryMaster c where c.continent=:origin)
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Weight for Region
    @Query("""
            select a.carrier, a.accNo, SUM(a.stdWeight) as totalNoOfWeightCount from AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
            where a.origin = b.code and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where
            b.continent in(select c.continent from RegionMaster c where c.regionName = :origin))
            group by a.carrier, a.accNo order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("origin") String origin);

    //Point Of Sales - Total Number of Booking Count for Airport
    @Query("""
            select a.eventDate as day, count(*) as bookingCount
            from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Point Of Sales - Total Number of Booking Count for Country
    @Query("""
            select a.eventDate as day, count(*) as bookingCount
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.countryCode = :country
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                 @Param("carrier") String carrier, @Param("country") String country);

    //Point Of Sales - Total Number of Booking Count for Continent
    @Query("""
            select a.eventDate as day,count(*) as bookingCount
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.continent = :continent
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                   @Param("carrier") String carrier, @Param("continent") String continent);


    //Point Of Sales - Total Number of Booking Count for Region
    @Query("""
            select a.eventDate as day,count(*) as bookingCount
            from   AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            join RegionMaster c on b.continent = c.continent
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and c.regionName = :region
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("region") String region);


    //Point Of Sales - Total Number of Weight Count for Airport
    @Query("""
            select a.eventDate as day, sum(a.stdWeight) as totalWeight
            from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    //Point Of Sales - Total Number of Weight  for Country
    @Query("""
            select a.eventDate as day, sum(a.stdWeight) as totalWeight
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.countryCode = :country
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("country") String country);

    //Point Of Sales - Total Number of Weight for Continent
    @Query("""
            select a.eventDate as day, sum(a.stdWeight) as totalWeight
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.continent = :continent
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                  @Param("carrier") String carrier, @Param("continent") String continent);

    //Point Of Sales - Total Number of Weight  for Region
    @Query("""
            select a.eventDate as day, sum(a.stdWeight) as totalWeight
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            join RegionMaster c on b.continent = c.continent
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and c.regionName = :region
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("region") String region);


    //Point Of Sales - Total Number of Volume  for Airport
    @Query("""
            select a.eventDate as day, sum(a.stdVol) as totalVolume
            from AdvanceFunctionAudit a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Point Of Sales - Total Number of Volume  for Country
    @Query("""
            select a.eventDate as day, sum(a.stdVol) as totalVolume
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.countryCode = :country
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("country") String country);

    //Point Of Sales - Total Number of Volume  for Continent
    @Query("""
            select a.eventDate as day, sum(a.stdVol) as totalVolume
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and b.continent=:continent
            group by a.eventDate
            order by day desc
            """)
    List<Object[]> getPointOfSalesVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                  @Param("carrier") String carrier, @Param("continent") String continent);

    //Point Of Sales - Total Number of Volume  for Region
    @Query("""
            select a.eventDate as day, sum(a.stdVol) as totalVolume
            from AdvanceFunctionAudit a
            join CityCountryMaster b on a.origin = b.code
            join RegionMaster c on b.continent = c.continent
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
            and a.carrier = :carrier
            and c.regionName = :region
            group by a.eventDate
            order by day desc
            """)
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
                                    from   dbo.AdvanceFunctionAudit a , dbo.ProductType p
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
                                    from   AdvanceFunctionAudit a
                                    where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                    and a.org=:originAirport
                                    and a.carrier=:carrier
                                    group by a.productCode
                                    ) m
                        			on s.productCode = m.productCode left join
            			(select a.productCode, COUNT(*) AS TOPPRODUCTS
                                    from   AdvanceFunctionAudit a
                                    where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                                             from   AdvanceFunctionAudit a, CityCountryMaster b
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier=:carrier
                                     						 and a.org = b.code
                                     				         and b.countryCode = :country
                                                             group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, COUNT(*) AS TOPPRODUCTS
                                                             from   AdvanceFunctionAudit a,  CityCountryMaster b
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                                             from   AdvanceFunctionAudit a,  CityCountryMaster b
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier=:carrier
                                     						 and a.org = b.code
                                     				         and b.continent = :continent
                                                             group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, COUNT(*) AS TOPPRODUCTS
                                                             from   AdvanceFunctionAudit a, CityCountryMaster b
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                    from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c
                                    where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                    and a.carrier=:carrier
                                    and a.org = b.code
            				        and b.continent = c.continent
            				        and c.regionName = :region
                                    group by a.productCode
                                    ) m
                        			on s.productCode = m.productCode left join
            			(select a.productCode, COUNT(*) AS TOPPRODUCTS
                                    from   AdvanceFunctionAudit a, CityCountryMaster b, RegionMaster c
                                    where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                     				group by a.productCode,p.description                                                             ) s left join
                                                 (select a.productCode, SUM(a.stdWeight) AS totalWeight
                                                             from   AdvanceFunctionAudit a
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier=:carrier
                                     						and a.org=:originAirport
                                     						group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, SUM(a.stdWeight) AS totalWeight
                                                             from   AdvanceFunctionAudit a
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                                             from   AdvanceFunctionAudit a,CityCountryMaster b
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier = :carrier
                                                             and a.org = b.code
                                     				and b.countryCode = :country
                                     						group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, SUM(a.stdWeight) AS totalWeight
                                                             from   AdvanceFunctionAudit a, CityCountryMaster b
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                                             from   AdvanceFunctionAudit a,CityCountryMaster b
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier = :carrier
                                                             and a.org = b.code
                                                             and b.continent = :continent
                                     						group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, SUM(a.stdWeight) AS totalWeight
                                                             from   AdvanceFunctionAudit a,CityCountryMaster b
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                                             from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier = :carrier
                                                             and a.org = b.code
                                                             and b.continent = c.continent
                                                             and c.regionName = :region
                                     						group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, SUM(a.stdWeight) AS totalWeight
                                                             from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                                             from   AdvanceFunctionAudit a
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier=:carrier
                                     						and a.org=:originAirport
                                     						group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, SUM(a.stdVolume) AS totalVolume
                                                             from   AdvanceFunctionAudit a
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                     				group by a.productCode,p.description                                                             ) s left join
                                                 (select a.productCode, SUM(a.stdVolume) AS totalVolume
                                                             from   AdvanceFunctionAudit a, CityCountryMaster b
                                                             where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                                             and a.carrier = :carrier
                                                             and a.org = b.code
                                     				and b.countryCode = :country
                                     						group by a.productCode
                                                             ) m
                                                 			on s.productCode = m.productCode left join
                                     			(select a.productCode, SUM(a.stdVolume) AS totalVolume
                                                             from   AdvanceFunctionAudit a,CityCountryMaster b
                                                             where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                    from   AdvanceFunctionAudit a, CityCountryMaster b
                                    where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
                                    and a.carrier = :carrier
                                    and a.org = b.code
                            and b.continent = :continent
            						group by a.productCode
                                    ) m
                        			on s.productCode = m.productCode left join
            			(select a.productCode, SUM(a.stdVolume) AS totalVolume
                                    from   AdvanceFunctionAudit a, CityCountryMaster b
                                    where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c
            where month(a.eventDate)= (month(:startDate)-1) and year(a.eventDate)= year(:startDate)
            and a.carrier = :carrier
            and a.org = b.code
            and b.continent = c.continent
            and c.regionName = :region
            group by a.productCode
            ) m
            on s.productCode = m.productCode left join
            (select a.productCode, SUM(a.stdVolume) AS totalVolume
            from   AdvanceFunctionAudit a,CityCountryMaster b, RegionMaster c
            where month(a.eventDate)= month(:startDate) and year(a.eventDate)=(year(:startDate)-1)
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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                              and a.CARRIER = :carrier
                              and a.ORG IN (SELECT b.CODE FROM  CITYCOUNTRYMASTER b WHERE b.COUNTRYCODE=:origin)
                              ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                             @Param("carrier") String carrier, @Param("origin") String origin);


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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                              and a.CARRIER = :carrier
                              and a.ORG IN (SELECT b.CODE FROM  CITYCOUNTRYMASTER b WHERE b.CONTINENT=:origin)
                              ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                               @Param("carrier") String carrier, @Param("origin") String origin);

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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG IN (SELECT b.CODE FROM  CITYCOUNTRYMASTER b WHERE
                               b.CONTINENT IN(SELECT e.CONTINENT FROM REGIONMASTER e WHERE e.REGIONNAME= :origin))
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("origin") String origin);

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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG in(SELECT b.CODE FROM CITYCOUNTRYMASTER b WHERE b.COUNTRYCODE = :origin)
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("origin") String origin);


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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG in(SELECT b.CODE FROM CITYCOUNTRYMASTER b WHERE b.CONTINENT = :origin)
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                              @Param("carrier") String carrier, @Param("origin") String origin);

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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG IN(SELECT b.CODE FROM CITYCOUNTRYMASTER b WHERE
                               b.CONTINENT IN(SELECT e.CONTINENT FROM REGIONMASTER e WHERE e.REGIONNAME= :origin))
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                           @Param("carrier") String carrier, @Param("origin") String origin);

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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG in(SELECT B.CODE FROM CITYCOUNTRYMASTER b WHERE b.COUNTRYCODE = :origin)
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                            @Param("carrier") String carrier, @Param("origin") String origin);

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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG in(SELECT b.CODE FROM CITYCOUNTRYMASTER b WHERE b.CONTINENT=:origin)
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                              @Param("carrier") String carrier, @Param("origin") String origin);

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
                                 a.*,
                                 (CASE
                                   WHEN a.ORG NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.ORG = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) OR a.DEST NOT IN (
                                     SELECT b.CODE
                                     FROM CITYCOUNTRYMASTER b
                                     JOIN AIRLINEHOSTCOUNTRYMASTER c ON b.COUNTRYCODE = c.HOSTCOUNTRYCODE
                                     JOIN ADVANCEFUNCTIONAUDIT a ON a.DEST = b.CODE
                                     WHERE c.CARRIERCODE = :carrier
                                   ) THEN 'true'
                                   ELSE 'false'
                                 END) AS category
                               FROM ADVANCEFUNCTIONAUDIT a WHERE a.EVENTDATE >= :startDate and a.EVENTDATE <= :endDate
                               and a.txnStatus <> 'E' and a.txnStatus <> '' and a.status = 'S'
                               and a.CARRIER = :carrier
                               and a.ORG IN(SELECT b.CODE FROM CITYCOUNTRYMASTER b WHERE
                               b.CONTINENT IN(SELECT e.CONTINENT FROM REGIONMASTER e WHERE e.REGIONNAME= :origin))
                               ) AS CategoryCTE
                             ON ac.category = CategoryCTE.category
                             GROUP BY ac.category
                             ORDER BY ac.category""",nativeQuery = true)

    List<Object[]> getTopDomesticInternationalWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                           @Param("carrier") String carrier, @Param("origin") String origin);


    @Query("""
            select count(*) from BranchAccount a  where MONTH(a.actionDate)=MONTH(:currentDate)
            and YEAR(a.actionDate)=YEAR(:currentDate)""")

    Object[] getNewAgentsInCurrentMonth(@Param("currentDate") LocalDateTime currentDate);

}
