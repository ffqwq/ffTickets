package com.yrh.ff_ticket.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = IDENTITY)//表示主码自增
    private int message_id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "msg")
    private String msg;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "update_time")
    private Date update_time;
}
