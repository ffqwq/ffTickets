package com.yrh.ff_ticket.converter;

import com.yrh.ff_ticket.dao.model.Users;
import com.yrh.ff_ticket.payload.dto.UserDto;

public class UserConverter {
    public static UserDto userToUserDto(Users users){
        return new UserDto(users.getUser_id(), users.getUser_name(), users.getBalance() ,users.getUserType().getType());
    }
}
