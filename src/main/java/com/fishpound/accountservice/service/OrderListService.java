package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.result.ResultOrderList;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderListService {
    OrderList getOne(String id);
    List<OrderList> findAllByUserId(String id);
    Map<String, Object> findAll(Integer page);
    boolean deleteOrderList(String id);
    boolean addOrderList(OrderList orderList);
}
