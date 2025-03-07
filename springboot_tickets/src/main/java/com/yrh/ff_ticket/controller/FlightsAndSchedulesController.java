package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.dao.model.FlightsAndSchedules;
import com.yrh.ff_ticket.dao.model.PairFlightsAndSchedules;
import com.yrh.ff_ticket.dao.service.FlightsAndSchedulesService;
import com.yrh.ff_ticket.dao.serviceImpl.FlightAndSchedulesServiceImpl;
import com.yrh.ff_ticket.payload.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flightsAndSchedules")
public class FlightsAndSchedulesController {

    @Autowired
    FlightsAndSchedulesService flightsAndSchedulesService=new FlightAndSchedulesServiceImpl();

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<FlightsAndSchedules>> findAll(){
        return new DataResponse<>(flightsAndSchedulesService.findAll());
    }

    @RequestMapping(value = "/findAllByStart_date",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<FlightsAndSchedules>> findAllByStart_date(@RequestParam("start_date")String start_date){
        return new DataResponse<>(flightsAndSchedulesService.findAllByStart_date(start_date));
    }

    @RequestMapping(value = "/findAllByStart_dateAndStart_cityAndEnd_city",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<List<FlightsAndSchedules>> findAllByStart_dateAndStart_cityAndEnd_city(@RequestBody Map<String,String> map)
    {
        String start_date=(String) map.get("start_date");
        String start_city=(String) map.get("start_city");
        String end_city=(String) map.get("end_city");
        return new DataResponse<>(flightsAndSchedulesService.findAllByStart_dateAndStart_cityAndEnd_city(start_date,start_city,end_city));
    }

    @RequestMapping(value = "/findAllByFlight_idAndStart_date",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<List<FlightsAndSchedules>> findAllByFlight_idAndStart_date(@RequestBody Map<String,String> map)
    {
        String flight_id=(String) map.get("flight_id");
        String start_date=(String) map.get("start_date");
        if(start_date==null||start_date.equals("")){
            return new DataResponse<>(false,"请输入正确的日期");
        }
        return new DataResponse<>(flightsAndSchedulesService.findAllByFlight_idAndStart_date(flight_id,start_date));
    }
    @RequestMapping(value = "/findTransitByStart_dateAndStart_cityAndEnd_city",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<List<PairFlightsAndSchedules>> findTransitByStart_dateAndStart_cityAndEnd_city(@RequestBody Map<String,String> map)
    {
        String start_date=(String) map.get("start_date");
        String start_city=(String) map.get("start_city");
        String end_city=(String) map.get("end_city");
        return new DataResponse<>(flightsAndSchedulesService.findTransitByStart_dateAndStart_cityAndEnd_city(start_date,start_city,end_city));
    }

}
