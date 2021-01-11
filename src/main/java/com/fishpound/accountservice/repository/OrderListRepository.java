package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderList, String> {
    OrderList getById(String id);
    Long countAllByOrOrderApply_Id(String oid);
//    List<OrderList> findAllByOrderApply_IdAnAndStatusNot(String id, Integer status);
//    List<OrderList> findAllByIdAndStatusNot(String id, Integer status);
}
