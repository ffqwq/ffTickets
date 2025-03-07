package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.dao.model.Messages;
import com.yrh.ff_ticket.dao.repository.MessagesRepository;
import com.yrh.ff_ticket.dao.service.MessagesService;
import com.yrh.ff_ticket.payload.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    MessagesRepository messagesRepository;

    @Override
    public List<Messages> findAll() {
        return messagesRepository.findAll();
    }

    @Override
    public Messages findMessagesByMessage_id(int message_id) {
        Optional<Messages> messages=messagesRepository.findById(message_id);
        if(messages.isEmpty()){
            return null;
        }
        return messages.get();
    }

    @Override
    public List<Messages> findMessagesByUser_id(String user_id) {
        List<Messages> l=messagesRepository.findMessagesByUser_id(user_id);
        l.sort(new Comparator<Messages>() {
            @Override
            public int compare(Messages o1, Messages o2) {
                return o2.getUpdate_time().compareTo(o1.getUpdate_time());
            }
        });
        return l;
    }

    @Override
    public synchronized DataResponse<Object> insertMessage(Messages messages) {
        if(messages==null){
            return new DataResponse<>(false,"传入为空，无法插入");
        }
        if(messagesRepository.existsById(messages.getMessage_id())){
            return new DataResponse<>(false,"数据已存在，无法插入");
        }
        messagesRepository.save(messages);
        return new DataResponse<>();
    }

    @Override
    public synchronized DataResponse<Messages> deleteMessage(int message_id) {
        Messages m=findMessagesByMessage_id(message_id);
        if(m==null)return new DataResponse<>(false,"消息不存在");
        messagesRepository.delete(m);
        return new DataResponse<>(m);
    }

    @Override
    public synchronized DataResponse<Object> editAndSaveMessage(int message_id, Messages messages) {
        if(message_id==0)return new DataResponse<>(false,"消息编号不能为0");
        if(messages==null)return new DataResponse<>(false,"消息为空");
        if(message_id < 0 )return new DataResponse<>(false,"编号有误");
        if(message_id!=messages.getMessage_id()){
            Optional<Messages> o=messagesRepository.findById(message_id);
            if(o.isPresent())return new DataResponse<>(false,"修改后的消息编号重复");
            messagesRepository.updateMessage_id(message_id,messages.getMessage_id());
        }
        messagesRepository.save(messages);//保存操作有值时自动覆盖
        return new DataResponse<>();
    }
}
