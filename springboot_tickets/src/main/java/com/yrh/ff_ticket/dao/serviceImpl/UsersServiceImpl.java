package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.converter.UserConverter;
import com.yrh.ff_ticket.dao.model.EUserType;
import com.yrh.ff_ticket.dao.model.Passengers;
import com.yrh.ff_ticket.dao.model.UserType;
import com.yrh.ff_ticket.dao.model.Users;
import com.yrh.ff_ticket.dao.repository.PassengersRepository;
import com.yrh.ff_ticket.dao.repository.UserTypeRepository;
import com.yrh.ff_ticket.dao.repository.UsersRepository;
import com.yrh.ff_ticket.dao.service.UsersService;
import com.yrh.ff_ticket.jwt.Token;
import com.yrh.ff_ticket.payload.dto.UserDto;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.util.BCryptUtil;
import com.yrh.ff_ticket.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    PassengersRepository passengersRepository;


    @Override
    public DataResponse<Token> userLogin(String user_id, String user_password,String user_type) {

        Optional<Users> op=usersRepository.findById(user_id);
        if(op.isEmpty()){
            return new DataResponse<>(false,"无此用户，请先注册");
        }
        if(BCryptUtil.decrypt(user_password,op.get().getUser_password())){
            if(op.get().getUserType().getType().equals(user_type)){
                Token token= new Token(TokenUtil.createUserJwt(user_id,user_type));
                return new DataResponse<>(token);
            }else {
                return new DataResponse<>(false,"权限错误");
            }
        }
        return new DataResponse<>(false,"密码错误");
    }

    @Override
    public DataResponse<Object> userRegister(String user_id, String user_name, String user_password) {
        Optional<Users> op=usersRepository.findById(user_id);
        if(!op.isEmpty()){
            return new DataResponse<>(false,"该用户已经注册");
        }
        Optional<UserType> userType=userTypeRepository.findById(EUserType.user.toString());
        Users users=new Users(user_id,user_name,user_password,0, userType.get());
        usersRepository.save(users);
        return new DataResponse<>();
    }

    @Override
    public DataResponse<List<UserDto>> findAll() {
        List<Users> list=usersRepository.findAll();
        List<UserDto> l=new ArrayList<>();
        for(Users users:list){
            l.add(UserConverter.userToUserDto(users));
        }
        return new DataResponse<>(l);
    }

    @Override
    public Users findUserByUser_id(String user_id) {
        Optional<Users> op=usersRepository.findById(user_id);
        if(op.isEmpty())return null;
        return op.get();
    }

    @Override
    public synchronized DataResponse<Object> insertUser(Users users) {
        if(users==null){
            return new DataResponse<>(false,"传入为空，无法插入");
        }
        if(usersRepository.existsById(users.getUser_id())){
            return new DataResponse<>(false,"数据已存在，无法插入");
        }
        usersRepository.save(users);
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> deleteUser(String user_id) {
        Users u=findUserByUser_id(user_id);
        if(u==null){
            return new DataResponse<>(false,"数据不存在，无法删除");
        }
        try {
            usersRepository.delete(u);
        }catch (Exception e){
            return new DataResponse<>(false,"请先删除该用户所有的订单");
        }
        return new DataResponse<>(u);
    }

    @Override
    public synchronized DataResponse<Object> editAndSaveUser(String user_id, Users users) {
        if(users==null)return new DataResponse<>(false,"用户为空");
        if(user_id==null || user_id.equals(""))return new DataResponse<>(false,"编号为空");
        if(!user_id.equals(users.getUser_id())){
            usersRepository.updateUser_Id(user_id,users.getUser_id());
        }
        String password=usersRepository.findById(users.getUser_id()).get().getUser_password();
        users.setUser_password(password);
        usersRepository.save(users);
        return new DataResponse<>();
    }

    @Override
    public DataResponse<Object> changePassword(String user_id, String oldPassword, String newPassword) {
        if(user_id==null||oldPassword==null||newPassword==null||user_id.equals("")||oldPassword.equals("")||newPassword.equals("")){
            return new DataResponse<>(false,"数据为空");
        }
        if(oldPassword.equals(newPassword)){
            return new DataResponse<>(false,"新旧密码相同");
        }
        Users u=findUserByUser_id(user_id);
        if(u==null){
            return new DataResponse<>(false,"数据不存在");
        }
        if(!BCryptUtil.decrypt(oldPassword,u.getUser_password())){
            return new DataResponse<>(false,"旧密码错误");
        }
        usersRepository.changePassword(user_id,BCryptUtil.encrypt(newPassword));
        return new DataResponse<>();
    }

    @Override
    public DataResponse<Object> changeName(String user_id, String user_name) {
        if(user_id==null||user_name==null||user_id.equals("")||user_name.equals("")){
            return new DataResponse<>(false,"数据为空");
        }
        Users u=findUserByUser_id(user_id);
        if(u==null){
            return new DataResponse<>(false,"数据不存在");
        }
        usersRepository.changeName(user_id,user_name);
        return new DataResponse<>();
    }

    @Override
    public DataResponse<Map<String,Object>> findUserInfo(String user_id) {
        if(user_id==null||user_id.equals("")){
            return new DataResponse<>(false,"用户编号为空");
        }
        Optional<Users> users=usersRepository.findById(user_id);
        if(users.isEmpty()){
            return new DataResponse<>(false,"没有这个用户");
        }
        Users user=users.get();
        List<Passengers> passengers=passengersRepository.findPassengersByUser_id(user_id);
        List<Map<String,String>> list=new ArrayList<>();
        for(Passengers p:passengers){
            Map<String,String> m=new HashMap<>();
            m.put("passenger_id",p.getPassenger_id());
            m.put("passenger_name",p.getPassenger_name());
            list.add(m);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("user_id",user.getUser_id());
        map.put("user_name",user.getUser_name());
        map.put("user_type",user.getUserType().getType());
        map.put("balance",user.getBalance());
        map.put("passengers",list);
        return new DataResponse<>(map);
    }

    @Override
    public synchronized DataResponse<Object> increaseUserBalance(String user_id, double amount) {
        if(user_id==null||user_id.equals("")){
            return new DataResponse<>(false,"用户编号为空");
        }
        Optional<Users> users=usersRepository.findById(user_id);
        if(users.isEmpty()){
            return new DataResponse<>(false,"没有这个用户");
        }
        try {
            usersRepository.increaseBalance(user_id,amount);
        }catch (Exception e){
            return new DataResponse<>(false,e.toString());
        }
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> reduceUserBalance(String user_id, double amount) {
        if(user_id==null||user_id.equals("")){
            return new DataResponse<>(false,"用户编号为空");
        }
        Optional<Users> users=usersRepository.findById(user_id);
        if(users.isEmpty()){
            return new DataResponse<>(false,"没有这个用户");
        }
        Users user=users.get();
        if(user.getBalance()<amount){
            return new DataResponse<>(false,"余额不足");
        }
        try {
            usersRepository.reduceBalance(user_id,amount);
        }catch (Exception e){
            return new DataResponse<>(false,e.toString());
        }
        return new DataResponse<>();
    }
}
