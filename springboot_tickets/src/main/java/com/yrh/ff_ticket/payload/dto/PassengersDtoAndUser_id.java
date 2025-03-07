package com.yrh.ff_ticket.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengersDtoAndUser_id {
    private String passenger_id;
    private String passenger_name;
    private String user_id;
}
