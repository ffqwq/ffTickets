package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.converter.PassengerConverter;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.model.Passengers;
import com.yrh.ff_ticket.dao.model.Users;
import com.yrh.ff_ticket.dao.repository.PassengersRepository;
import com.yrh.ff_ticket.dao.repository.UsersRepository;
import com.yrh.ff_ticket.dao.service.PassengersService;
import com.yrh.ff_ticket.payload.dto.PassengersDto;
import com.yrh.ff_ticket.payload.dto.PassengersDtoAndUser_id;
import com.yrh.ff_ticket.payload.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PassengersServiceImpl implements PassengersService {

    @Autowired
    PassengersRepository passengersRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<PassengersDto> findPassengersByUser_id(String user_id) {
        List<Passengers> l=passengersRepository.findPassengersByUser_id(user_id);
        List<PassengersDto> list=new ArrayList<>();
        for(Passengers p:l){
            list.add(PassengerConverter.passengerToPassengerDto(p));
        }
        return list;
    }

    @Override
    public DataResponse<Object> insertPassenger(String passenger_id, String passenger_name, String user_id) {
        Optional<Users> u=usersRepository.findById(user_id);
        if(u.isEmpty()){
            return new DataResponse<>(false,"用户不存在");
        }

        Users user=u.get();
        try {
            Passengers p=new Passengers(passenger_id,passenger_name,user);
            passengersRepository.save(p);
        }catch (Exception e){
            return new DataResponse<>(false,e.toString());
        }
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> deletePassenger(String passenger_id) {
        if(!passengersRepository.existsById(passenger_id)){
            return new DataResponse<>(false,"数据不存在");
        }
        passengersRepository.deleteById(passenger_id);
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> editAndSavePassenger(String passenger_id, PassengersDtoAndUser_id passengers) {
        if(passengers==null)return new DataResponse<>(false,"乘客为空");
        if(passenger_id ==null || passenger_id.equals("") )return new DataResponse<>(false,"编号有误");
        if(!passenger_id.equals(passengers.getPassenger_id())){
            Optional<Passengers> p=passengersRepository.findById(passengers.getPassenger_id());
            if(p.isPresent())return new DataResponse<>(false,"修改后的乘客号重复");
            passengersRepository.updatePassenger_id(passenger_id,passengers.getPassenger_id());
        }
        passengersRepository.updatePassenger(passengers.getPassenger_id(),passengers.getPassenger_name(),passengers.getUser_id());//保存操作有值时自动覆盖
        return new DataResponse<>();
    }

    @Override
    public List<PassengersDtoAndUser_id> findAll() {
        List<Passengers> l=passengersRepository.findAll();
        List<PassengersDtoAndUser_id> list=new ArrayList<>();
        for (Passengers p:l){
            list.add(PassengerConverter.passengerToPassengersDtoAndUser_id(p));
        }
        return list;
    }
}
