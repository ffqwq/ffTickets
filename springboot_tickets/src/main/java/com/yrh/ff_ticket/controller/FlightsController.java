package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.converter.JsonConverter;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.dao.model.Flights;
import com.yrh.ff_ticket.dao.service.FlightsService;
import com.yrh.ff_ticket.dao.serviceImpl.FlightsServiceImpl;
import com.yrh.ff_ticket.role.RoleCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class FlightsController {
    @Autowired
    FlightsService flightsService=new FlightsServiceImpl();

    //查询
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody//以json格式返回
    public DataResponse<Flights> findFlightsById(@RequestParam("flight_id") String flight_id){
        return new DataResponse<>(flightsService.findFlightsByFlight_id(flight_id));
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<Flights>> findAll(){
        return new DataResponse<>(flightsService.findAll());
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/insertFlights",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> insertFlights(@RequestBody List<Flights> list){
        return flightsService.insertFlights(list);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/insertFlight",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> insertFlight(@RequestBody Flights flights){
        return flightsService.insertFlight(flights);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/deleteFlight",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse<Object> deleteFlight(@RequestParam("flight_id") String flight_id){
        return flightsService.deleteFlight(flight_id);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/editAndSaveFlight",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> editAndSaveFlight(@RequestBody Map<String,Object> mp){
        Flights flights;
        try {
            flights=JsonConverter.JsonToFlight((Map) mp.get("flight"));
        }catch (Exception e){
            return new DataResponse<>(e,false,"出错");
        }
        return flightsService.editAndSaveFlight((String) mp.get("flight_id"), flights);
    }
}
