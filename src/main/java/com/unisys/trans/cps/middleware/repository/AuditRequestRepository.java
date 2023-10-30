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

    @Query("select a.origin, a.destination, COUNT(*) AS TOPLANE\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin = :originAirport\n" +
            "group by a.origin,a.destination\n" +
            "order by TOPLANE desc LIMIT 5")
    List<Object[]> getTopLanesBookingAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                               @Param("carrier") String carrier, @Param("originAirport") String originAirport);

    @Query("select a.origin, a.destination, COUNT(*) AS TOPLANE\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:country)\n" +
            "group by a.origin,a.destination\n" +
            "order by TOPLANE desc LIMIT 5")
    List<Object[]> getTopLanesBookingCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("country") String country);

    @Query("select a.origin, a.destination, COUNT(*) AS TOPLANE\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in(select b.code from CityCountryMaster b where b.continent=:continent)\n" +
            "group by a.origin,a.destination\n" +
            "order by TOPLANE desc LIMIT 5")
    List<Object[]> getTopLanesBookingContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                             @Param("carrier") String carrier, @Param("continent") String continent);

    @Query("select a.origin, a.destination, COUNT(*) AS TOPLANE\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in (select b.code from CityCountryMaster b where b.continent in (select c.continent from RegionMaster c where c.regionName =:region))\n" +
            "group by a.origin,a.destination\n" +
            "order by TOPLANE desc LIMIT 5")
    List<Object[]> getTopLanesBookingRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("region") String region);

    @Query("select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin = :originAirport\n" +
            "group by a.origin,a.destination\n" +
            "order by totalWeight desc LIMIT 5")
    List<Object[]> getTopLanesWeightAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    @Query("select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:country)\n" +
            "group by a.origin,a.destination\n" +
            "order by totalWeight desc LIMIT 5")
    List<Object[]> getTopLanesWeightCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("country") String country);


    @Query("select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in(select b.code from CityCountryMaster b where b.continent=:continent)\n" +
            "group by a.origin,a.destination\n" +
            "order by totalWeight desc LIMIT 5")
    List<Object[]> getTopLanesWeightContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("continent") String continent);

    @Query("select a.origin, a.destination, SUM(a.stdWeight) AS totalWeight\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in (select b.code from CityCountryMaster b where b.continent in (select c.continent from RegionMaster c where c.regionName =:region))\n" +
            "group by a.origin,a.destination\n" +
            "order by totalWeight desc LIMIT 5")
    List<Object[]> getTopLanesWeightRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                           @Param("carrier") String carrier, @Param("region") String region);


    @Query("select a.origin, a.destination, SUM(a.stdVol) AS totalVolume\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin = :originAirport\n" +
            "group by a.origin,a.destination\n" +
            "order by totalVolume desc LIMIT 5")
    List<Object[]> getTopLanesVolumeAirport(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("originAirport") String originAirport);


    @Query("select a.origin, a.destination, SUM(a.stdVol) AS totalVolume\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in(select b.code from CityCountryMaster b where b.countryCode=:country)\n" +
            "group by a.origin,a.destination\n" +
            "order by totalVolume desc LIMIT 5")
    List<Object[]> getTopLanesVolumeCountry(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                            @Param("carrier") String carrier, @Param("country") String country);


    @Query("select a.origin, a.destination, SUM(a.stdVol) AS totalVolume\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in(select b.code from CityCountryMaster b where b.continent=:continent)\n" +
            "group by a.origin,a.destination\n" +
            "order by totalVolume desc LIMIT 5")
    List<Object[]> getTopLanesVolumeContinent(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                              @Param("carrier") String carrier, @Param("continent") String continent);

    @Query("select a.origin, a.destination, SUM(a.stdVol) AS totalVolume\n" +
            "from   AuditRequest a  \n" +
            "where a.eventDate >= :startDate and a.eventDate <= :endDate\n" +
            "and a.carrier = :carrier\n" +
            "and a.origin in (select b.code from CityCountryMaster b where b.continent in (select c.continent from RegionMaster c where c.regionName =:region))\n" +
            "group by a.origin,a.destination\n" +
            "order by totalVolume desc LIMIT 5")
    List<Object[]> getTopLanesVolumeRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                           @Param("carrier") String carrier, @Param("region") String region);

}
