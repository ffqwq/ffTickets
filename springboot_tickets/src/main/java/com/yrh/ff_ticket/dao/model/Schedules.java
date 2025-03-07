package com.yrh.ff_ticket.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@IdClass(SchedulesPrimaryKey.class)
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedules {

    @Id
    @Column(name = "flight_id")
    private String flight_id;

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
