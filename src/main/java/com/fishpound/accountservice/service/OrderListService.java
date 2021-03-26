package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderList;

import java.util.List;

public interface OrderListService {
    OrderList getOne(String id);
    List<OrderList> findAllByUserId(String id);
    boolean deleteOrderList(String id);
    boolean addOrderList(OrderList orderList);
}
