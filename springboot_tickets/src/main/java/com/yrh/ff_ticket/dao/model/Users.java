package com.yrh.ff_ticket.dao.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
//    @GeneratedValue(strategy = IDENTITY)//表示主码自增
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "user_password")
    private String user_password;

    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "type")
    private UserType userType;

}
