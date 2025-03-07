package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.converter.JsonConverter;
import com.yrh.ff_ticket.dao.model.Users;
import com.yrh.ff_ticket.dao.service.UsersService;
import com.yrh.ff_ticket.dao.serviceImpl.UsersServiceImpl;
import com.yrh.ff_ticket.jwt.Token;
import com.yrh.ff_ticket.payload.dto.UserDto;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.role.RoleCheck;
import com.yrh.ff_ticket.util.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService=new UsersServiceImpl();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Token> userLogin(@RequestBody Map map){
        String user_id = (String) map.get("user_id");
        String user_password = (String) map.get("user_password");
        String user_type = (String) map.get("user_type");
        return usersService.userLogin(user_id,user_password,user_type);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> userRegister(@RequestBody Map map){
        String user_id = (String) map.get("user_id");
        String user_name = (String) map.get("user_name");
        String user_password= BCryptUtil.encrypt((String) map.get("user_password"));
        return usersService.userRegister(user_id,user_name,user_password);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<UserDto>> findAll(){
        return usersService.findAll();
    }


    @RoleCheck("admin")
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> insertUser(@RequestBody Map<String,Object> mp){
        Users users;
        try {
            users= JsonConverter.JsonToUser(mp);
        }catch (Exception e){
            return new DataResponse<>(e,false,"出错");
        }
        return usersService.insertUser(users);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/deleteUser",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse<Object> deleteUser(@RequestParam("user_id") String user_id){
        return usersService.deleteUser(user_id);
    }

    @RequestMapping(value = "/editAndSaveUser",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> editAndSaveUser(@RequestBody Map<String,Object> mp){
        Users users;
        try {
            users= JsonConverter.JsonToUserExceptPassword((Map) mp.get("user"));
        }catch (Exception e){
            return new DataResponse<>(e,false,"出错");
        }
        return usersService.editAndSaveUser((String) mp.get("user_id"), users);
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> changePassword(@RequestBody Map<String,String> mp){
        String user_id=mp.get("user_id");
        String oldPassword=mp.get("oldPassword");
        String newPassword=mp.get("newPassword");
        return usersService.changePassword(user_id,oldPassword,newPassword);
    }

    @RequestMapping(value = "/changeName",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> changeName(@RequestBody Map<String,String> mp){
        String user_id=mp.get("user_id");
        String user_name=mp.get("user_name");
        return usersService.changeName(user_id,user_name);
    }

    @RequestMapping(value = "/findUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<Map<String,Object>> findUserInfo(@RequestParam("user_id") String user_id){
        return usersService.findUserInfo(user_id);
    }

    @RequestMapping(value = "/increaseUserBalance",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> increaseUserBalance(@RequestBody Map<String,Object> map){
        String user_id=(String) map.get("user_id");
        try {
            double amount=Double.parseDouble((String)map.get("amount"));
            return usersService.increaseUserBalance(user_id,amount);
        }catch (Exception e){
            return new DataResponse<>(false,"请传入正确格式的钱数");
        }
    }

    @RequestMapping(value = "/reduceUserBalance",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> reduceUserBalance(@RequestBody Map<String,Object> map){
        String user_id=(String) map.get("user_id");
        try {
            double amount=Double.parseDouble((String)map.get("amount"));
            return usersService.reduceUserBalance(user_id,amount);
        }catch (Exception e){
            return new DataResponse<>(false,"请传入正确格式的钱数");
        }
    }
}