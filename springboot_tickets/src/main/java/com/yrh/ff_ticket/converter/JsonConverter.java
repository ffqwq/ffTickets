package com.yrh.ff_ticket.converter;

import com.yrh.ff_ticket.dao.model.*;
import com.yrh.ff_ticket.dao.repository.UserTypeRepository;
import com.yrh.ff_ticket.payload.dto.PassengersDtoAndUser_id;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class JsonConverter {

    @Autowired
    UserTypeRepository userTypeRepository;
    public static Flights JsonToFlight(Map map) throws ParseException {
        Flights flights=new Flights();
        flights.setFlight_id((String) map.get("flight_id"));
        flights.setAirline((String) map.get("airline"));
        flights.setAirtype((String) map.get("airtype"));

        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        Date start_time=ft.parse((String) map.get("start_time"));
        Date end_time=ft.parse((String) map.get("end_time"));

        flights.setStart_time(start_time);
        flights.setEnd_time(end_time);
        flights.setStart_city((String)map.get("start_city") );
        flights.setEnd_city((String)map.get("end_city") );
        flights.setStart_airport((String)map.get("start_airport"));
        flights.setEnd_airport((String) map.get("end_airport"));
        return flights;
    }

    public static Schedules JsonToSchedule(Map map) throws ParseException {
        Schedules schedules=new Schedules();
        schedules.setFlight_id((String) map.get("flight_id"));


        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date=ft.parse((String) map.get("start_date"));
        schedules.setStart_date(start_date);

        schedules.setNums((Integer) map.get("nums"));
        schedules.setPrice((Double) map.get("price") );
        schedules.setPunctuality((Boolean) map.get("punctuality"));
        return schedules;
    }

    public static Users JsonToUser(Map map) throws ParseException{
        Users users=new Users();
        users.setUser_id((String) map.get("user_id"));
        users.setUser_name((String) map.get("user_name"));
        users.setUser_password((String) map.get("user_password"));
        users.setBalance((int) map.get("balance"));
        try {
            EUserType eUserType=UserTypeConverter.StringToEUserType((String) map.get("type"));
        }catch (Exception e){
            throw new RuntimeException("用户类型出错");
        }
        users.setUserType(new UserType((String) map.get("type")));
        return users;
    }

    public static Users JsonToUserExceptPassword(Map map) throws ParseException{
        Users users=new Users();
        users.setUser_id((String) map.get("user_id"));
        users.setUser_name((String) map.get("user_name"));
//        users.setUser_password((String) map.get("user_password"));
        users.setBalance((double) map.get("balance"));
        try {
            EUserType eUserType=UserTypeConverter.StringToEUserType((String) map.get("type"));
        }catch (Exception e){

        }
        users.setUserType(new UserType((String) map.get("type")));
        return users;
    }

    public static Orders JsonToOrder(Map map) throws ParseException {
        Orders orders=new Orders();
        orders.setOrder_id((Integer) map.get("order_id"));
        orders.setUser_id((String) map.get("user_id"));
        orders.setPassenger_id((String) map.get("passenger_id"));
        orders.setFlight_id((String) map.get("flight_id"));

        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date=ft1.parse((String) map.get("start_date"));
        orders.setStart_date(start_date);

        orders.setAmount((Double) map.get("amount"));

        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date update_time=ft2.parse((String) map.get("update_time"));
        orders.setUpdate_time(update_time);

        orders.setIs_pay((boolean) map.get("isPay"));

        return orders;
    }
    public static Messages JsonToMessage(Map map) throws ParseException {
        Messages messages=new Messages();
        messages.setMessage_id((Integer) map.get("message_id"));
        messages.setUser_id((String) map.get("user_id"));
        messages.setMsg((String) map.get("msg"));
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date update_time=ft.parse((String) map.get("update_time"));
        messages.setUpdate_time(update_time);
        return messages;
    }

    public static PassengersDtoAndUser_id JsonToPassengerDtoAndUser_id(Map map){

        String passenger_id=(String) map.get("passenger_id");
        String passenger_name=(String) map.get("passenger_name");
        String user_id=(String)map.get("user_id");
        return new PassengersDtoAndUser_id(passenger_id,passenger_name,user_id);
    }
}
