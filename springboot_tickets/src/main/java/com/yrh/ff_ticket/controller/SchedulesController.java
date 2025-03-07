package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.converter.JsonConverter;
import com.yrh.ff_ticket.dao.model.Schedules;
import com.yrh.ff_ticket.dao.service.SchedulesService;
import com.yrh.ff_ticket.dao.serviceImpl.SchedulesServiceImpl;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.role.RoleCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class SchedulesController {

    @Autowired
    SchedulesService schedulesService=new SchedulesServiceImpl();

    //查询
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody//以json格式返回
    public DataResponse<Schedules> findSchedulesByFlight_idAndStart_date(
            @RequestParam(value = "flight_id") String flight_id,
            @RequestParam(value = "start_date") String start_date)
    {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date=ft.parse(start_date);
        }catch (Exception e){
            return new DataResponse<>(null,false,"传入日期格式不正确");
        }
        return new DataResponse<>(schedulesService.findSchedulesByFlight_idAndStart_date(flight_id,date));
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<Schedules>> findAll(){
        return new DataResponse<>(schedulesService.findAll());
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/insertSchedule",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> insertSchedule(@RequestBody Schedules schedules){
        return schedulesService.insertSchedule(schedules);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/deleteSchedule",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse<Object> deleteSchedule(
            @RequestParam("flight_id") String flight_id,
            @RequestParam("start_date") String start_date
    ){
        return schedulesService.deleteSchedule(flight_id,start_date);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/editAndSaveSchedule",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> editAndSaveSchedule(@RequestBody Map<String,Object> mp){
        Schedules schedule;
        try {
            schedule= JsonConverter.JsonToSchedule((Map) mp.get("schedule"));
        }catch (Exception e){
            return new DataResponse<>(e,false,"转换Date出错");
        }
        String flight_id=(String) mp.get("flight_id");
        String start_date=(String) mp.get("start_date");
        return schedulesService.editAndSaveSchedule(flight_id,start_date,schedule);
    }
}
