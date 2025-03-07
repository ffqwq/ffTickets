package com.yrh.ff_ticket.converter;

import com.yrh.ff_ticket.dao.model.Passengers;
import com.yrh.ff_ticket.payload.dto.PassengersDtoAndUser_id;
import com.yrh.ff_ticket.payload.dto.PassengersDto;

public class PassengerConverter {
    public static PassengersDto passengerToPassengerDto(Passengers passengers){
        return new PassengersDto(passengers.getPassenger_id(),passengers.getPassenger_name());
    }

    public static PassengersDtoAndUser_id passengerToPassengersDtoAndUser_id(Passengers passengers){
        return new PassengersDtoAndUser_id(passengers.getPassenger_id(),passengers.getPassenger_name(),passengers.getUsers().getUser_id());
    }
}
