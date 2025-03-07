package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PassengersRepository extends JpaRepository<Passengers,String> {
    @Query(value = "select * from passengers where user_id = ?",nativeQuery = true)
    List<Passengers> findPassengersByUser_id(String user_id);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update passengers p set p.passenger_id = ?2 where p.passenger_id = ?1",nativeQuery = true)
    void updatePassenger_id(String oldId,String newId);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update passengers p set p.passenger_name = ?2 , p.user_id = ?3 where p.passenger_id = ?1",nativeQuery = true)
    void updatePassenger(String passenger_id,String passenger_name,String user_id);
}
