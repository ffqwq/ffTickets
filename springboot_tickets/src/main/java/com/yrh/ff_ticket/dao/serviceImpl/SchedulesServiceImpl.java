package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.dao.model.Messages;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.model.Schedules;
import com.yrh.ff_ticket.dao.model.SchedulesPrimaryKey;
import com.yrh.ff_ticket.dao.repository.OrdersRepository;
import com.yrh.ff_ticket.dao.repository.SchedulesRepository;
import com.yrh.ff_ticket.dao.service.MessagesService;
import com.yrh.ff_ticket.dao.service.SchedulesService;
import com.yrh.ff_ticket.dao.service.UsersService;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SchedulesServiceImpl implements SchedulesService {

    @Autowired
    SchedulesRepository schedulesRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UsersService usersService=new UsersServiceImpl();
    @Autowired
    MessagesService messagesService=new MessagesServiceImpl();

    @Override
    public Schedules findSchedulesByFlight_idAndStart_date(String flight_id, Date start_date) {
        return schedulesRepository.findSchedulesByFlight_idAndStart_date(flight_id,start_date);
    }

    @Override
    public List<Schedules> findAll() {
        return schedulesRepository.findAll();
    }

    @Override
    public synchronized DataResponse<Object> insertSchedule(Schedules schedules) {
        if(schedules==null){
            return new DataResponse<>(null,false,"传入为空，无法插入");
        }
        SchedulesPrimaryKey schedulesPrimaryKey=new SchedulesPrimaryKey(schedules.getFlight_id(),schedules.getStart_date());

        if(schedulesRepository.existsById(schedulesPrimaryKey)){
            return new DataResponse<>(null,false,"数据已存在，无法插入");
        }
        schedulesRepository.save(schedules);
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> deleteSchedule(String flight_id, String start_date) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date=ft.parse(start_date);
        }catch (Exception e){
            return new DataResponse<>(false,"传入日期格式不正确");
        }
        SchedulesPrimaryKey schedulesPrimaryKey=new SchedulesPrimaryKey(flight_id,date);
        if(!schedulesRepository.existsById(schedulesPrimaryKey)){
            return new DataResponse<>(false,"数据不存在，无法删除");
        }
        if(DateUtil.getCurrentDateTimeString("yyyy-MM-dd").compareTo(start_date)>0){
            schedulesRepository.deleteById(schedulesPrimaryKey);
            return new DataResponse<>();
        }
        List<Orders> list= ordersRepository.findOrdersByFlight_idAndStart_date(flight_id,start_date);
        for(Orders o:list){
            Messages m=new Messages(0,o.getUser_id(),"",new Date());
            if(o.getIs_pay()){
                usersService.increaseUserBalance(o.getUser_id(),o.getAmount());
                m.setMsg("您将于"+DateUtil.DateToString(o.getStart_date(),"yyyy-MM-dd")
                        +"起飞的航班:"+o.getFlight_id()
                        +"因故取消，您所支付的钱款已返还。敬请谅解");
            }else {
                m.setMsg("您将于"+DateUtil.DateToString(o.getStart_date(),"yyyy-MM-dd")
                        +"起飞的航班:"+o.getFlight_id()
                        +"因故取消。您尚未支付。敬请谅解");
            }
            messagesService.insertMessage(m);
            ordersRepository.delete(o);
        }
        schedulesRepository.deleteById(schedulesPrimaryKey);
        return new DataResponse<>();
    }

    @Override
    public DataResponse<Object> editAndSaveSchedule(String flight_id, String start_date, Schedules schedule) {
        if(flight_id==null || flight_id.equals(""))return new DataResponse<>(false,"航班编号为空");
        if(start_date==null)return new DataResponse<>(false,"日期为空");
        if(schedule==null)return new DataResponse<>(false,"调度为空");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(schedule.getStart_date());
        if((!flight_id.equals(schedule.getFlight_id())) || (!start_date.equals(strDate))){
            schedulesRepository.updateFlight_IdAndStart_date(flight_id,start_date,schedule.getFlight_id(),strDate);
        }
        schedulesRepository.save(schedule);
        return new DataResponse<>();
    }
}
