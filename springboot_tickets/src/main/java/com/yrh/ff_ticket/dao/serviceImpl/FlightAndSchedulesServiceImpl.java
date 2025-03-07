package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.converter.FlightsAndSchedulesConverter;
import com.yrh.ff_ticket.dao.model.FlightsAndSchedules;
import com.yrh.ff_ticket.dao.model.PairFlightsAndSchedules;
import com.yrh.ff_ticket.dao.repository.FlightsAndSchedulesRepository;
import com.yrh.ff_ticket.dao.service.FlightsAndSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FlightAndSchedulesServiceImpl implements FlightsAndSchedulesService {

    @Autowired
    FlightsAndSchedulesRepository flightsAndSchedulesRepository;

    @Override
    public List<FlightsAndSchedules> findAll() {
        return flightsAndSchedulesRepository.findAll();
    }

    @Override
    public List<FlightsAndSchedules> findAllByStart_date(String start_date) {
        return flightsAndSchedulesRepository.findAllByStart_date(start_date);
    }

    @Override
    public List<FlightsAndSchedules> findAllByStart_dateAndStart_cityAndEnd_city(String start_date, String start_city, String end_city) {
        return  flightsAndSchedulesRepository.findAllByStart_dateAndStart_cityAndEnd_city(start_date,start_city,end_city);
    }

    @Override
    public List<FlightsAndSchedules> findAllByFlight_idAndStart_date(String flight_id,String start_date) {
        return flightsAndSchedulesRepository.findAllByFlight_idAndStart_date(flight_id,start_date);
    }

    @Override//存在中转的调度计划
    public List<PairFlightsAndSchedules> findTransitByStart_dateAndStart_cityAndEnd_city(String start_date, String start_city, String end_city) {
        List<Map<String,Object>> l=flightsAndSchedulesRepository.findTransitByStart_dateAndStart_cityAndEnd_city(start_date,start_city,end_city);
        return FlightsAndSchedulesConverter.ListMapToListPair(l);
    }
}
