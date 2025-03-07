package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.Orders;
import com.yrh.ff_ticket.dao.model.Passengers;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update orders o set o.order_id = ?2 where o.order_id = ?1",nativeQuery = true)
    void updateOrder_Id(int oldId,int newId);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update orders o set o.is_pay = true where o.order_id = ?",nativeQuery = true)
    void isPayToTrue(int order_id);

    @Query(value = "select * from orders o where o.flight_id = ?1 and o.start_date = ?2 ",nativeQuery = true)
    List<Orders> findOrdersByFlight_idAndStart_date(String flight_id, String start_date);

    @Query(value = "select * from orders o where o.user_id = ?1",nativeQuery = true)
    List<Orders> findOrdersByUser_id(String user_id);
}
