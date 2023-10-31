package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.AuditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRequestRepository extends JpaRepository<AuditRequest, BigInteger> {

    //Top Lanes - Total Number of Booking Count for Airport
    @Query("""
            select a.origin, a.destination, COUNT(*) AS TOPLANE
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin = :originAirport
            group by a.origin,a.destination
            order by TOPLANE desc LIMIT 5
            """)
    List<Object[]> getTopLanesBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    //Top Lanes - Total Number of Booking Count for Country
    @Query("""
            select a.origin, a.destination, COUNT(*) AS TOPLANE
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:country)
            group by a.origin,a.destination
            order by TOPLANE desc LIMIT 5
            """)
    List<Object[]> getTopLanesBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("country") String country);

    //Top Lanes - Total Number of Booking Count for Continent
    @Query("""
            select a.origin, a.destination, COUNT(*) AS TOPLANE
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.continent=:continent)
            group by a.origin,a.destination
            order by TOPLANE desc LIMIT 5
            """)
    List<Object[]> getTopLanesBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("continent") String continent);


    //Top Lanes - Total Number of Booking Count for Region
    @Query("""
            select a.origin, a.destination, COUNT(*) AS TOPLANE
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in (select b.code from CityCountryMaster b where b.continent in (select c.continent from RegionMaster c where c.regionName =:region))
            group by a.origin,a.destination
            order by TOPLANE desc LIMIT 5
            """)
    List<Object[]> getTopLanesBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("region") String region);


    //Top Lanes - Total Number of Weight Count for Airport
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
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
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:country)
            group by a.origin,a.destination
            order by totalWeight desc LIMIT 5
            """)
    List<Object[]> getTopLanesWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("country") String country);

    //Top Lanes - Total Number of Weight  for Continent
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.continent=:continent)
            group by a.origin,a.destination
            order by totalWeight desc LIMIT 5
            """)
    List<Object[]> getTopLanesWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Lanes - Total Number of Weight  for Region
    @Query("""
            select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in (select b.code from CityCountryMaster b where b.continent in (select c.continent from RegionMaster c where c.regionName =:region))
            group by a.origin,a.destination
            order by totalWeight desc LIMIT 5
            """)
    List<Object[]> getTopLanesWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                           @Param("carrier") String carrier, @Param("region") String region);


    //Top Lanes - Total Number of Volume  for Airport
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            from AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
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
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:country)
            group by a.origin,a.destination
            order by totalVolume desc LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("country") String country);

    //Top Lanes - Total Number of Volume  for Continent
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.continent=:continent)
            group by a.origin,a.destination
            order by totalVolume desc LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("continent") String continent);

    //Top Lanes - Total Number of Volume  for Region
    @Query("""
            select a.origin, a.destination, SUM(a.stdVol) AS totalVolume
            from   AuditRequest a
            where a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in (select b.code from CityCountryMaster b where b.continent in (select c.continent from RegionMaster c where c.regionName =:region))
            group by a.origin,a.destination
            order by totalVolume desc LIMIT 5
            """)
    List<Object[]> getTopLanesVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                           @Param("carrier") String carrier, @Param("region") String region);


    //Top Agents - Total Number of Booking Count for AirPort
    @Query("""
            select a.branchID, a.carrier, b.corporation, COUNT(*) as totalNoOfBookingCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin = :origin
            group by a.branchID, a.carrier, b.corporation order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Booking Count for Country
    @Query("""
            select a.branchID, a.carrier, b.corporation, COUNT(*) as totalNoOfBookingCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:origin)
            group by a.accNo, a.branchID, a.carrier, b.corporation order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Booking Count for Continent
    @Query("""
            select a.branchID, a.carrier, b.corporation, COUNT(*) as totalNoOfBookingCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.continent=:origin)
            group by a.branchID, a.carrier, b.corporation order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Booking Count for Region
    @Query("""
            select a.branchID, a.carrier, a.origin, d.corporation, COUNT(*) as totalNoOfBookingCount from AuditRequest a, CityCountryMaster b, RegionMaster c, BranchProfile d
            where a.origin = b.code and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.branchID=d.branchId
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where
            b.continent in(select c.continent from RegionMaster c where c.regionName= :origin))
            group by a.branchID, a.carrier, a.origin order by totalNoOfBookingCount desc LIMIT 5""")

    List<Object[]> getTopAgentsBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Volume for AirPort
    @Query("""
            select a.branchID, a.carrier, b.corporation, SUM(a.stdVol) as totalNoOfVolumeCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin = :origin
            group by  a.branchID, a.carrier, b.corporation order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Volume for Country
    @Query("""
            select  a.branchID, a.carrier, b.corporation, SUM(a.stdVol)  as totalNoOfVolumeCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:origin)
            group by a.branchID, a.carrier, b.corporation order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Volume for Continent
    @Query("""
            select  a.branchID, a.carrier, b.corporation, SUM(a.stdVol) as totalNoOfVolumeCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.continent=:origin)
            group by  a.branchID, a.carrier, b.corporation order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Volume for Region
    @Query("""
            select  a.branchID, a.carrier, a.origin, d.corporation, SUM(a.stdVol) as totalNoOfVolumeCount from AuditRequest a, CityCountryMaster b, RegionMaster c, BranchProfile d
            where a.origin = b.code and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.branchID=d.branchId
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where
            b.continent in(select c.continent from RegionMaster c where c.regionName= :origin))
            group by a.branchID, a.carrier, a.origin,d.corporation order by totalNoOfVolumeCount desc LIMIT 5""")

    List<Object[]> getTopAgentsVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Weight for AirPort
    @Query("""
            select a.branchID, a.carrier, b.corporation, SUM(a.stdWeight) as totalNoOfWeightCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin = :origin
            group by a.branchID, a.carrier, b.corporation order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Weight for Country
    @Query("""
            select a.branchID, a.carrier, b.corporation, SUM(a.stdWeight) as totalNoOfWeightCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:origin)
            group by a.branchID, a.carrier, b.corporation order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("origin") String origin);


    //Top Agents - Total Number of Weight for Continent
    @Query("""
            select a.branchID, a.carrier, b.corporation, SUM(a.stdWeight) as totalNoOfWeightCount from AuditRequest a, BranchProfile b
            where a.branchID= b.branchId and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where b.continent=:origin)
            group by a.branchID, a.carrier, b.corporation order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                               @Param("carrier") String carrier, @Param("origin") String origin);

    //Top Agents - Total Number of Weight for Region
    @Query("""
            select a.branchID, a.carrier, a.origin, d.corporation, SUM(a.stdWeight) as totalNoOfWeightCount from AuditRequest a, CityCountryMaster b, RegionMaster c, BranchProfile d
            where a.origin = b.code and a.eventDate >= :startDate and a.eventDate <= :endDate
            and a.branchID=d.branchId
            and a.carrier = :carrier
            and a.origin in(select b.code from CityCountryMaster b where
            b.continent in(select c.continent from RegionMaster c where c.regionName = :origin))
            group by a.branchID, a.carrier, a.origin, d.corporation order by totalNoOfWeightCount desc LIMIT 5""")

    List<Object[]> getTopAgentsWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("origin") String origin);

}
