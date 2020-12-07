package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderList, String> {
    OrderList getById(String id);
    List<OrderList> findAllByUserIdAndStatusNotLike(String id, Integer status);
}
