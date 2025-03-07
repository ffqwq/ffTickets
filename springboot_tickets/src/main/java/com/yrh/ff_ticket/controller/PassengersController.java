package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.converter.JsonConverter;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.model.Passengers;
import com.yrh.ff_ticket.dao.service.PassengersService;
import com.yrh.ff_ticket.dao.serviceImpl.PassengersServiceImpl;
import com.yrh.ff_ticket.payload.dto.PassengersDto;
import com.yrh.ff_ticket.payload.dto.PassengersDtoAndUser_id;
import com.yrh.ff_ticket.payload.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/passengers")
public class PassengersController {

    @Autowired
    PassengersService passengersService=new PassengersServiceImpl();

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<PassengersDtoAndUser_id>> findAll(){
        return new DataResponse<>(passengersService.findAll());
    }

    @RequestMapping(value = "/findPassengersByUser_id",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<PassengersDto>> findPassengersByUser_id(
            @RequestParam("user_id") String user_id){
        return new DataResponse<>(passengersService.findPassengersByUser_id(user_id));
    }

    @RequestMapping(value = "/insertPassenger",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> insertPassenger(@RequestBody Map<String,Object> mp){
        String passenger_id=(String) mp.get("passenger_id");
        String passenger_name=(String) mp.get("passenger_name");
        String user_id=(String)mp.get("user_id");
        return passengersService.insertPassenger(passenger_id,passenger_name,user_id);
    }

    @RequestMapping(value = "/deletePassenger",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse<Object> deletePassenger(@RequestParam("passenger_id") String passenger_id){
        return passengersService.deletePassenger(passenger_id);
    }

    @RequestMapping(value = "/editAndSavePassenger",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> editAndSavePassenger(@RequestBody Map<String,Object> mp){
        PassengersDtoAndUser_id p;
        try {
            p = JsonConverter.JsonToPassengerDtoAndUser_id((Map) mp.get("passenger"));
        }catch (Exception e){
            return new DataResponse<>(e,false,"出错");
        }
        return passengersService.editAndSavePassenger((String) mp.get("passenger_id"), p);
    }
}
