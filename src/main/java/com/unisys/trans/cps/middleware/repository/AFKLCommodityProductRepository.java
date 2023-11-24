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
           select c.commodityCode, c.commodity from AFKLCommodityProduct c
           where c.commodityCode IN :codes
           and c.carrier = :carrier
           order by c.commodity
           LIMIT 1
           """)
    List<Object[]> getTopAFKLCommodityDescription(@Param("codes") List<String> codes, @Param("carrier") String carrier);


}
