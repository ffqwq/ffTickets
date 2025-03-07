package com.yrh.ff_ticket.converter;

import com.yrh.ff_ticket.dao.model.FlightsAndSchedules;
import com.yrh.ff_ticket.dao.model.PairFlightsAndSchedules;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class FlightsAndSchedulesConverter {
    public static PairFlightsAndSchedules MapToPair(Map<String,Object> map){
        FlightsAndSchedules first=new FlightsAndSchedules(
                (String)map.get("first_flight_id"),
                (String) map.get("first_airline"),
                (String) map.get("first_airtype"),
                (Date) map.get("first_start_time"),
                (Date) map.get("first_end_time"),
                (String) map.get("first_start_city"),
                (String) map.get("first_end_city"),
                (String) map.get("first_start_airport"),
                (String) map.get("first_end_airport"),
                (Date) map.get("first_start_date"),
                (int) map.get("first_nums"),
                (double) map.get("first_price"),
                (boolean)map.get("first_punctuality")
        );
        FlightsAndSchedules second=new FlightsAndSchedules(
                (String)map.get("second_flight_id"),
                (String) map.get("second_airline"),
                (String) map.get("second_airtype"),
                (Date) map.get("second_start_time"),
                (Date) map.get("second_end_time"),
                (String) map.get("second_start_city"),
                (String) map.get("second_end_city"),
                (String) map.get("second_start_airport"),
                (String) map.get("second_end_airport"),
                (Date) map.get("second_start_date"),
                (int) map.get("second_nums"),
                (double) map.get("second_price"),
                (boolean)map.get("second_punctuality")
        );
        return new PairFlightsAndSchedules(first,second);
    }
    public static List<PairFlightsAndSchedules> ListMapToListPair(List<Map<String,Object>> l){
        List<PairFlightsAndSchedules> list=new ArrayList<>();
        for(Map<String,Object> map:l){
            list.add(MapToPair(map));
        }
        return list;
    }
}
