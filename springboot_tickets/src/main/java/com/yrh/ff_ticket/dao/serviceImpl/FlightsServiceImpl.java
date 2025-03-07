package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.dao.model.Flights;
import com.yrh.ff_ticket.dao.repository.FlightsRepository;
import com.yrh.ff_ticket.dao.service.FlightsService;
import com.yrh.ff_ticket.payload.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class FlightsServiceImpl implements FlightsService {

    @Autowired
    FlightsRepository flightsRepository;

    @Override
    public Flights findFlightsByFlight_id(String flight_id) {
        Optional<Flights> op=flightsRepository.findById(flight_id);
        if(op.isEmpty())return null;
        return op.get();
    }

    @Override
    public List<Flights> findAll() {
        return flightsRepository.findAll();
    }

    @Override
    public synchronized DataResponse<Object> insertFlight(Flights flight) {
        if(flight==null){
            return new DataResponse<>(false,"传入为空，无法插入");
        }
        if(flightsRepository.existsById(flight.getFlight_id())){
            return new DataResponse<>(false,"数据已存在，无法插入");
        }
        flightsRepository.save(flight);
        return new DataResponse<>();
    }



    @Override
    public synchronized DataResponse<Object> insertFlights(List<Flights> list) {
        //判断list不为null且不为空
        if (CollectionUtils.isEmpty(list)) {
            return new DataResponse<>(false,"传入列表为空，无法插入");
        }
        int errorNum=0;
        //然后遍历List
        for(Flights flight:list){
            Flights f=findFlightsByFlight_id(flight.getFlight_id());
            if(f==null){
                flightsRepository.save(flight);
            }else{
                errorNum++;
            }
        }

        if(errorNum!=0){
            return new DataResponse<>(false,errorNum+"个数据已存在，未插入");
        }else {
            return new DataResponse<>();
        }
    }

    @Override
    public synchronized DataResponse<Object> deleteFlight(String flight_id) {
        Flights f=findFlightsByFlight_id(flight_id);
        if(f==null){
            return new DataResponse<>(false,"数据不存在，无法删除");
        }
        try{
            flightsRepository.delete(f);
        }catch (Exception e){
            return new DataResponse<>(false,"请先删除该航班所有的调度");
        }
        return new DataResponse<>(f);
    }

    @Override
    public synchronized DataResponse<Object> editAndSaveFlight(String flight_id,Flights flight) {
        if(flight==null)return new DataResponse<>(false,"航班为空");
        if(flight_id==null || flight_id.equals(""))return new DataResponse<>(false,"编号为空");
        if(!flight_id.equals(flight.getFlight_id())){
            flightsRepository.updateFlight_Id(flight_id,flight.getFlight_id());
        }
        flightsRepository.save(flight);//保存操作有值时自动覆盖
        return new DataResponse<>();
    }
}
