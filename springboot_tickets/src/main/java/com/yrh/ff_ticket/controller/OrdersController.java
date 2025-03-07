package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.converter.JsonConverter;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.service.OrdersService;
import com.yrh.ff_ticket.dao.serviceImpl.OrdersServiceImpl;
import com.yrh.ff_ticket.role.RoleCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService=new OrdersServiceImpl();

    //查询
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody//以json格式返回
    public DataResponse<Orders> findOrdersById(@RequestParam("order_id") int order_id){
        return new DataResponse<>(ordersService.findOrdersByOrder_id(order_id));
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<Orders>> findAll(){
        return new DataResponse<>(ordersService.findAll());
    }

    @RequestMapping(value = "/findOrdersByUser_id",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<Orders>> findOrdersByUser_id(@RequestParam("user_id") String user_id){
        return new DataResponse<>(ordersService.findOrdersByUser_id(user_id));
    }



    @RequestMapping(value = "/insertOrder",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> insertOrder(@RequestBody Orders orders){
        return ordersService.insertOrder(orders);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/deleteOrder",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse<Object> deleteOrder(@RequestParam("order_id") int order_id){
        return ordersService.deleteOrder(order_id);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/editAndSaveOrder",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> editAndSaveOrder(@RequestBody Map<String,Object> mp){
        Orders orders;
        try {
            orders=JsonConverter.JsonToOrder((Map) mp.get("order"));
        }catch (Exception e){
            return new DataResponse<>(e,false,"出错");
        }
        return ordersService.editAndSaveOrder((int) mp.get("order_id"), orders);
    }

    @RequestMapping(value = "/payOrder",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> payOrder(@RequestBody Map<String,Object> mp) throws InterruptedException {
        int order_id=(int) mp.get("order_id");
        return ordersService.payOrder(order_id);
    }

    @RequestMapping(value = "/refundTicket",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> refundTicket(@RequestBody Map<String,Object> mp){
        int order_id=(int) mp.get("order_id");
        return ordersService.refundTicket(order_id);
    }

    @RequestMapping(value = "/changeTicket",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> changeTicket(@RequestBody Map<String,Object> mp){
        int order_id=(int) mp.get("order_id");
        String start_date=(String) mp.get("start_date");
        return ordersService.changeTicket(order_id,start_date);
    }
}

