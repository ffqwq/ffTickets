package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FlightsRepository extends JpaRepository<Flights,String> {
    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)
    @Query(value = "update flights f set f.flight_id = ?2 where f.flight_id = ?1",nativeQuery = true)
    void updateFlight_Id(String oldId,String newId);
}