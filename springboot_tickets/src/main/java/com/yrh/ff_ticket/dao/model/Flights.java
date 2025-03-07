package com.yrh.ff_ticket.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flights {
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

}
