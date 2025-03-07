package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.Flights;
import com.yrh.ff_ticket.payload.response.DataResponse;

import java.util.List;


public interface FlightsService {
    Flights findFlightsByFlight_id(String flight_id);
    List<Flights> findAll();
    DataResponse<Object> insertFlights(List<Flights> list);

    DataResponse<Object> insertFlight(Flights flight);
    DataResponse<Object> deleteFlight(String flight_id);

    DataResponse<Object> editAndSaveFlight(String flight_id,Flights flight);
}
