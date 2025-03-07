package com.yrh.ff_ticket.dao.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulesPrimaryKey implements Serializable {
    //多个主键要使用类表示
    private String flight_id;
    private Date start_date;

}
