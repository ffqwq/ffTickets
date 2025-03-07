package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.Users;
import com.yrh.ff_ticket.jwt.Token;
import com.yrh.ff_ticket.payload.dto.UserDto;
import com.yrh.ff_ticket.payload.response.DataResponse;

import java.util.List;
import java.util.Map;

public interface UsersService {
    DataResponse<Token> userLogin(String user_id, String user_password,String user_type);
    DataResponse<Object> userRegister(String user_id,String user_name,String user_password);
    DataResponse<List<UserDto>> findAll();

    Users findUserByUser_id(String user_id);
    DataResponse<Object> insertUser(Users users);
    DataResponse<Object> deleteUser(String user_id);
    DataResponse<Object> editAndSaveUser(String user_id,Users users);

    DataResponse<Object> changePassword(String user_id,String oldPassword,String newPassword);

    DataResponse<Object> changeName(String user_id,String user_name);

    DataResponse<Map<String,Object>> findUserInfo(String user_id);

    DataResponse<Object> increaseUserBalance(String user_id, double amount);
    DataResponse<Object> reduceUserBalance(String user_id, double amount);
}
