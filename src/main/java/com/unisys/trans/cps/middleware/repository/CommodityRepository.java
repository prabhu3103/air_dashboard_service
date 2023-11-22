package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, String> {

    @Query("""
           select c.code, c.description from Commodity c
           where c.code IN :codes
           and c.carrier = :carrier
           order by code
           LIMIT 5
            """)
    List<Object[]> getTopCommodityDescription(@Param("codes") List<String> codes, @Param("carrier") String carrier);

}
