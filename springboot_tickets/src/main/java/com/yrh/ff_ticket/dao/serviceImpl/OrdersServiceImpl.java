package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.dao.model.Flights;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.model.Users;
import com.yrh.ff_ticket.dao.repository.FlightsRepository;
import com.yrh.ff_ticket.dao.repository.OrdersRepository;
import com.yrh.ff_ticket.dao.repository.SchedulesRepository;
import com.yrh.ff_ticket.dao.repository.UsersRepository;
import com.yrh.ff_ticket.dao.service.OrdersService;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.util.DateUtil;
import com.yrh.ff_ticket.util.RefundChangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    FlightsRepository flightsRepository;
    @Autowired
    SchedulesRepository schedulesRepository;


    @Override
    public Orders findOrdersByOrder_id(int order_id) {
        Optional<Orders> op=ordersRepository.findById(order_id);
        if(op.isEmpty())return null;
        return op.get();
    }

    @Override
    public List<Orders> findOrdersByUser_id(String user_id) {
        return ordersRepository.findOrdersByUser_id(user_id);
    }

    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public synchronized DataResponse<Object> insertOrder(Orders orders) {
        if(orders==null){
            return new DataResponse<>(false,"传入为空，无法插入");
        }
        if(ordersRepository.existsById(orders.getOrder_id())){
            return new DataResponse<>(false,"数据已存在，无法插入");
        }
        try{
            ordersRepository.save(orders);
        }catch (Exception e){
            return new DataResponse<>(false,"请输入已存在的用户编号，航班编号，乘客身份证号");
        }
        return new DataResponse<>();
    }

    @Override
    public DataResponse<Object> deleteOrder(int order_id) {
        Orders o=findOrdersByOrder_id(order_id);
        if(o==null){
            return new DataResponse<>(false,"数据不存在，无法删除");
        }
        ordersRepository.delete(o);
        return new DataResponse<>(o);
    }

    @Override
    public DataResponse<Object> editAndSaveOrder(int order_id, Orders orders) {
        if(order_id==0)return new DataResponse<>(false,"订单号不能为0");
        if(orders==null)return new DataResponse<>(false,"订单为空");
        if(order_id < 0 )return new DataResponse<>(false,"编号有误");
        if(order_id!=orders.getOrder_id()){
            Optional<Orders> o=ordersRepository.findById(orders.getOrder_id());
            if(o.isPresent())return new DataResponse<>(false,"修改后的订单号重复");
            ordersRepository.updateOrder_Id(order_id,orders.getOrder_id());
        }
        ordersRepository.save(orders);//保存操作有值时自动覆盖
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> payOrder(int order_id) throws InterruptedException {
        //检查
        Orders orders=findOrdersByOrder_id(order_id);
        if(orders==null)return new DataResponse<>(false,"订单不存在");
        String user_id=orders.getUser_id();
        Optional<Users> users=usersRepository.findById(user_id);
//        System.out.println("第一断点---------");
        if(users.isEmpty())return new DataResponse<>(false,"用户为空");
        Users user=users.get();

        double amount=orders.getAmount();
        double balance=user.getBalance();
        if(amount>balance){
            return new DataResponse<>(false,"余额不足");
        }
        try {
            usersRepository.reduceBalance(user_id,amount);

        }catch (Exception e){
            return new DataResponse<>(false,"出错");
        }
//        System.out.println("第二断点---------");
//        Thread.sleep(4000);
        ordersRepository.isPayToTrue(order_id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("第三断点---------");
        schedulesRepository.reduceTicketNum(orders.getFlight_id(),sdf.format(orders.getStart_date()),1);
//        System.out.println("第四断点---------");
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Object> refundTicket(int order_id){

        Orders orders=findOrdersByOrder_id(order_id);
        if(orders==null)return new DataResponse<>(false,"订单不存在");

        Optional<Users> users=usersRepository.findById(orders.getUser_id());
        if(users.isEmpty())return new DataResponse<>(false,"用户为空");
        Users user=users.get();

        Optional<Flights> flights=flightsRepository.findById(orders.getFlight_id());
        if(flights.isEmpty())return new DataResponse<>(false,"航班为空");
        Flights flight=flights.get();

        Date dateTime;
        try {
            dateTime= DateUtil.A_YMD_B_HMS(orders.getStart_date(),flight.getStart_time());
        }catch (Exception e){
            return new DataResponse<>(false,e.toString());
        }

        long flyDateTime=dateTime.getTime();
        System.out.println(flyDateTime);
        long nowDateTime=System.currentTimeMillis();
        System.out.println(nowDateTime);
        double refundFee = RefundChangeUtil.getRefundFee(flyDateTime,nowDateTime);
        System.out.println(refundFee);
        String str = String.format("%.2f",orders.getAmount()*(1-refundFee));
        double amount = Double.parseDouble(str);

        try {
            usersRepository.increaseBalance(user.getUser_id(),amount);
        }catch (Exception e){
            return new DataResponse<>(false,"出错");
        }
        ordersRepository.delete(orders);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        schedulesRepository.increaseTicketNum(orders.getFlight_id(),sdf.format(orders.getStart_date()),1);
        return new DataResponse<>("本次手续费为原票价的"+refundFee+"，您将返还："+amount+"元");
    }


    @Override
    public synchronized DataResponse<Object> changeTicket(int order_id, String start_date) {


        Orders orders=findOrdersByOrder_id(order_id);
        if(orders==null)return new DataResponse<>(false,"订单不存在");

        Optional<Users> users=usersRepository.findById(orders.getUser_id());
        if(users.isEmpty())return new DataResponse<>(false,"用户为空");
        Users user=users.get();

        Optional<Flights> flights=flightsRepository.findById(orders.getFlight_id());
        if(flights.isEmpty())return new DataResponse<>(false,"航班为空");
        Flights flight=flights.get();
        Date dateTime;
        try {
            dateTime= DateUtil.A_YMD_B_HMS(orders.getStart_date(),flight.getStart_time());
        }catch (Exception e){
            return new DataResponse<>(false,e.toString());
        }

        long flyDateTime=dateTime.getTime();
        long nowDateTime=System.currentTimeMillis();

        double changeFee = RefundChangeUtil.getChangeFee(flyDateTime,nowDateTime);

        String str = String.format("%.2f",orders.getAmount()*(1-changeFee));
        double amount = Double.parseDouble(str);


        try {
            usersRepository.increaseBalance(user.getUser_id(),amount);
        }catch (Exception e){
            return new DataResponse<>(false,"出错");
        }
        ordersRepository.delete(orders);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        schedulesRepository.increaseTicketNum(orders.getFlight_id(),sdf1.format(orders.getStart_date()),1);
        Date d;
        try {
            SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd" );
            d= sdf.parse(start_date);
        }catch (Exception e){
            return new DataResponse<>(false,"日期转换出错");
        }
        orders.setStart_date(d);
        orders.setUpdate_time(new Date(System.currentTimeMillis()));
        orders.setIs_pay(false);
        try{
            ordersRepository.save(orders);
        }catch (Exception e){
            return new DataResponse<>(false,"请输入已存在的用户编号，航班编号，乘客身份证号");
        }
        return new DataResponse<>("改签成功！\n"+
                "本次手续费为原票价的"+changeFee+"，您将返还："+amount+"元\n"
                +"请前往订单页面支付新航班订单");
    }
}