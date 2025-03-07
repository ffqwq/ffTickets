package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.FlightsAndSchedules;
import com.yrh.ff_ticket.dao.model.SchedulesPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlightsAndSchedulesRepository extends JpaRepository<FlightsAndSchedules, SchedulesPrimaryKey> {

    String findTransitByStart_dateAndStart_cityAndEnd_cityValue=
            """
                    select c.flight_id first_flight_id,
                    c.airline first_airline,
                    c.airtype first_airtype,
                    c.start_time first_start_time,
                    c.end_time first_end_time,
                    c.start_city first_start_city,
                    c.end_city first_end_city,
                    c.start_airport first_start_airport,
                    c.end_airport first_end_airport,
                    c.start_date first_start_date,
                    c.nums first_nums,
                    c.price first_price,
                    c.punctuality first_punctuality,
                    d.flight_id second_flight_id,
                    d.airline second_airline,
                    d.airtype second_airtype,
                    d.start_time second_start_time,
                    d.end_time second_end_time,
                    d.start_city second_start_city,
                    d.end_city second_end_city,
                    d.start_airport second_start_airport,
                    d.end_airport second_end_airport,
                    d.start_date second_start_date,
                    d.nums second_nums,
                    d.price second_price,
                    d.punctuality second_punctuality
                    from
                    (
                        select * from flightsAndSchedules a
                        where(a.start_date = ?1 and a.start_city = ?2 and a.end_city != ?3)
                    ) c
                    join
                    (
                        select * from flightsAndSchedules b
                        where(b.start_date = ?1 and b.start_city != ?2 and b.end_city = ?3)
                    ) d
                    on c.end_city = d.start_city and c.end_time < d.start_time""";
    @Query(value = "select *from flightsAndSchedules where start_date = ?",nativeQuery = true)
    List<FlightsAndSchedules> findAllByStart_date(String start_time);

    @Query(value = "select *from flightsAndSchedules where start_date = ?1 and start_city = ?2 and end_city = ?3",nativeQuery = true)
    List<FlightsAndSchedules>  findAllByStart_dateAndStart_cityAndEnd_city(String start_date, String start_city, String end_city);

    @Query(value = "select *from flightsAndSchedules where flight_id = ?1 and start_date = ?2 ",nativeQuery = true)
    List<FlightsAndSchedules> findAllByFlight_idAndStart_date(String flight_id, String start_date);

    @Query(value = findTransitByStart_dateAndStart_cityAndEnd_cityValue,nativeQuery = true)
    List<Map<String,Object>> findTransitByStart_dateAndStart_cityAndEnd_city(String start_date, String start_city, String end_city);
}