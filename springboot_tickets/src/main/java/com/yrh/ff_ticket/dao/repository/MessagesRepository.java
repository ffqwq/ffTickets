package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.Messages;
import com.yrh.ff_ticket.payload.response.DataResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages,Integer> {

    @Query(value = "select * from messages where user_id = ?",nativeQuery = true)
    List<Messages> findMessagesByUser_id(String user_id);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update messages m set m.message_id = ?2 where m.message_id = ?1",nativeQuery = true)
    void updateMessage_id(int oldId,int newId);
}
