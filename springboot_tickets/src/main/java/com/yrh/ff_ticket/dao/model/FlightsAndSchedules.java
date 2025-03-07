package com.yrh.ff_ticket.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@IdClass(SchedulesPrimaryKey.class)
@Table(name = "flightsandschedules")//映射视图
@Data
@NoArgsConstructor
@AllArgsConstructor
//驼峰结构默认加_,不应当使用
public class FlightsAndSchedules {
    @Id//数据库主属性
    @Column(name = "flight_id")
    private String flight_id;

    @Column(name = "airline")
    private String airline;

    @Column(name = "airtype")
    private String airtype;

    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    @Column(name = "start_time")
    private Date start_time;

    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    @Column(name = "end_time")
    private Date end_time;

    @Column(name = "start_city")
    private String start_city;

    @Column(name = "end_city")
    private String end_city;

    @Column(name = "start_airport")
    private String start_airport;

    @Column(name = "end_airport")
    private String end_airport;

    @Id
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "nums")
    private int nums;

    @Column(name = "price")
    private double price;

    @Column(name = "punctuality")
    private boolean punctuality;
}
