package com.unisys.trans.cps.middleware.repository;

import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerInquiryRepository extends JpaRepository<ContactQuery, String> {

    @Query("SELECT COUNT(c)  FROM   ContactQuery c WHERE  c.carrier = ?1 and c.date >= ?2")
    public int getCount(String carrierCode, LocalDateTime date);

    @Query("SELECT c  FROM   ContactQuery c WHERE c.carrier = ?1 and c.date >= ?2")
    public List<ContactQuery> getContactQuery(String carrier, LocalDateTime date);

}
