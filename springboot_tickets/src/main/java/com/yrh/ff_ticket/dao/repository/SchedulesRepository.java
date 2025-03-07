package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.Schedules;
import com.yrh.ff_ticket.dao.model.SchedulesPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, SchedulesPrimaryKey> {
    @Query(value = "select * from schedules where flight_id = ?1 and start_date = ?2 ",nativeQuery = true)
    //nativeQuery=true表示使用原生sql
    Schedules findSchedulesByFlight_idAndStart_date(String flight_id, Date start_date);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update schedules s set s.flight_id = ?3 , s.start_date = ?4" +
            " where s.flight_id = ?1 and s.start_date = ?2 "
            ,nativeQuery = true)
    void updateFlight_IdAndStart_date(
            String old_flight_id,
            String old_start_date,
            String new_flight_id,
            String new_start_date
    );

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update schedules s set s.nums = s.nums - ?3 where s.flight_id = ?1 and s.start_date=?2",nativeQuery = true)
    void reduceTicketNum(String flight_id,String start_date,int nums);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update schedules s set s.nums = s.nums + ?3 where s.flight_id = ?1 and s.start_date=?2",nativeQuery = true)
    void increaseTicketNum(String flight_id,String start_date,int nums);
}
