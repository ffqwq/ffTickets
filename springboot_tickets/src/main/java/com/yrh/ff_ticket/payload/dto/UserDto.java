package com.yrh.ff_ticket.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String user_id;

    private String user_name;

//    private String user_password;

    private double balance;

    private String type;
}