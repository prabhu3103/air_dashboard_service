package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.AFKLCommodityProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFKLCommodityProductRepository extends JpaRepository<AFKLCommodityProduct, String> {

    @Query("""         
            SELECT DISTINCT c.commodityCode, c.commodity
            FROM AFKLCommodityProduct c
            WHERE c.commodityCode IN :codes
            AND c.carrier = :carrier
            ORDER BY c.commodityCode
            """)
    List<Object[]> getTopAFKLCommodityDescription(@Param("codes") List<String> codes, @Param("carrier") String carrier);


}
