package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.Flights;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.payload.response.DataResponse;

import java.util.Date;
import java.util.List;

public interface OrdersService {
    Orders findOrdersByOrder_id(int order_id);
    List<Orders> findOrdersByUser_id(String user_id);
    List<Orders> findAll();
    DataResponse<Object> insertOrder(Orders orders);
    DataResponse<Object> deleteOrder(int order_id);

    DataResponse<Object> editAndSaveOrder(int order_id,Orders orders);

    DataResponse<Object> payOrder(int order_id) throws InterruptedException;

    DataResponse<Object> refundTicket(int order_id);

    DataResponse<Object> changeTicket(int order_id,String start_date);
}
