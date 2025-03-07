package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.model.Passengers;
import com.yrh.ff_ticket.payload.dto.PassengersDto;
import com.yrh.ff_ticket.payload.dto.PassengersDtoAndUser_id;
import com.yrh.ff_ticket.payload.response.DataResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PassengersService {

    List<PassengersDto> findPassengersByUser_id(String user_id);
    DataResponse<Object> insertPassenger(String passenger_id,String passenger_name,String user_id);
    DataResponse<Object> deletePassenger(String passenger_id);
    DataResponse<Object> editAndSavePassenger(String passenger_id, PassengersDtoAndUser_id passengers);
    List<PassengersDtoAndUser_id> findAll();
}
