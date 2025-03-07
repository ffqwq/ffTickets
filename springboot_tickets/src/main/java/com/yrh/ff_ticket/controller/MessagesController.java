package com.yrh.ff_ticket.controller;

import com.yrh.ff_ticket.converter.JsonConverter;
import com.yrh.ff_ticket.dao.model.Messages;
import com.yrh.ff_ticket.dao.service.MessagesService;
import com.yrh.ff_ticket.dao.serviceImpl.MessagesServiceImpl;
import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.role.RoleCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    MessagesService messagesService=new MessagesServiceImpl();

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse<List<Messages>> findAll(){
        return new DataResponse<>(messagesService.findAll());
    }

    @RequestMapping(value = "/findMessagesByMessage_id", method = RequestMethod.GET)
    @ResponseBody//以json格式返回
    public DataResponse<Messages> findMessagesByMessage_id(@RequestParam("message_id") int message_id){
        Messages messages=messagesService.findMessagesByMessage_id(message_id);
        if(messages==null)return new DataResponse<>(false,"不存在该消息");
        return new DataResponse<>(messages);
    }
    //查询
    @RequestMapping(value = "/findMessagesByUser_id", method = RequestMethod.GET)
    @ResponseBody//以json格式返回
    public DataResponse<List<Messages>> findMessagesByUser_id(@RequestParam("user_id") String user_id){
        return new DataResponse<>(messagesService.findMessagesByUser_id(user_id));
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/insertMessage", method = RequestMethod.POST)
    @ResponseBody//以json格式返回
    public DataResponse<Object> insertMessage(@RequestBody Messages messages){
        return messagesService.insertMessage(messages);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/deleteMessage",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse<Messages> deleteMessage(@RequestParam("message_id") int message_id){
        return messagesService.deleteMessage(message_id);
    }

    @RoleCheck("admin")
    @RequestMapping(value = "/editAndSaveMessage",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<Object> editAndSaveMessage(@RequestBody Map<String,Object> mp){
        Messages messages;
        try {
            messages = JsonConverter.JsonToMessage((Map) mp.get("message"));
        }catch (Exception e){
            return new DataResponse<>(e,false,"出错");
        }
        return messagesService.editAndSaveMessage((int) mp.get("message_id"), messages);
    }
}