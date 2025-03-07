package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.Schedules;
import com.yrh.ff_ticket.payload.response.DataResponse;

import java.util.Date;
import java.util.List;

public interface SchedulesService {

    Schedules findSchedulesByFlight_idAndStart_date(String flight_id,Date start_date);
    List<Schedules> findAll();
    DataResponse<Object> insertSchedule(Schedules schedules);
    DataResponse<Object> deleteSchedule(String flight_id,String start_date);

    DataResponse<Object> editAndSaveSchedule(String flight_id,String start_date,Schedules schedule);
}