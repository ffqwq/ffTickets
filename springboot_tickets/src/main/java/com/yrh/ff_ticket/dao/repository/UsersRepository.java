package com.yrh.ff_ticket.dao.repository;

import com.yrh.ff_ticket.dao.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends JpaRepository<Users,String> {
    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update users u set u.user_id = ?2 where u.user_id = ?1",nativeQuery = true)
    void updateUser_Id(String oldId,String newId);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update users u set u.user_password = ?2 where u.user_id = ?1",nativeQuery = true)
    void changePassword(String user_id,String newPassword);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update users u set u.user_name = ?2 where u.user_id = ?1",nativeQuery = true)
    void changeName(String user_id,String user_name);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update users u set u.balance = u.balance + ?2 where u.user_id = ?1",nativeQuery = true)
    void increaseBalance(String user_id,double amount);

    @Transactional//注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update users u set u.balance = u.balance - ?2 where u.user_id = ?1",nativeQuery = true)
    void reduceBalance(String user_id,double amount);
}
