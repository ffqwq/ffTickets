package com.yrh.ff_ticket.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = IDENTITY)//表示主码自增
    private int order_id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "passenger_id")
    private String passenger_id;

    @Column(name = "flight_id")
    private String flight_id;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "amount")
    private double amount;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "update_time")
    private Date update_time;

    @Column(name = "is_pay")
    //isPay驼峰结构会被自动转成is_pay而数据库里面仍是isPay，这样就找不到列了
    private boolean is_pay;

    public void setIs_pay(boolean is_pay) {
        this.is_pay = is_pay;
    }
    public boolean getIs_pay(){
        return this.is_pay;
    }
}
