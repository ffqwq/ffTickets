package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.FlightsAndSchedules;
import com.yrh.ff_ticket.dao.model.PairFlightsAndSchedules;

import java.util.Date;
import java.util.List;

public interface FlightsAndSchedulesService {
    List<FlightsAndSchedules> findAll();
    List<FlightsAndSchedules> findAllByStart_date(String start_date);

    List<FlightsAndSchedules> findAllByStart_dateAndStart_cityAndEnd_city(String start_date,String start_city,String end_city);
    List<FlightsAndSchedules> findAllByFlight_idAndStart_date(String flight_id,String start_date);

    List<PairFlightsAndSchedules> findTransitByStart_dateAndStart_cityAndEnd_city(String start_date, String start_city, String end_city);
}
