package com.yrh.ff_ticket.dao.service;

import com.yrh.ff_ticket.dao.model.Messages;
import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.payload.response.DataResponse;

import java.util.List;

public interface MessagesService {
    List<Messages> findAll();
    Messages findMessagesByMessage_id(int message_id);
    List<Messages> findMessagesByUser_id(String user_id);
    DataResponse<Object> insertMessage(Messages messages);
    DataResponse<Messages> deleteMessage(int message_id);
    DataResponse<Object> editAndSaveMessage(int message_id,Messages messages);
}
