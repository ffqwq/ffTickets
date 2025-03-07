package com.yrh.ff_ticket.converter;

import com.yrh.ff_ticket.dao.model.EUserType;

public class UserTypeConverter {

    public static EUserType StringToEUserType(String s){
        if(s==null||s.equals(""))throw new RuntimeException("用户权限转换出错");
        if(s.equals(EUserType.user.toString())){
            return EUserType.user;
        }else if(s.equals(EUserType.admin)){
            return EUserType.admin;
        }else throw new RuntimeException("用户权限转换出错");
    }
}
