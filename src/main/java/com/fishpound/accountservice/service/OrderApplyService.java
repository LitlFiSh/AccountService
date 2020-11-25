package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderApply;

import java.util.List;

public interface OrderApplyService {
    void addOrder(OrderApply orderApply);
    void updateOrder(OrderApply orderApply);
    void deleteOrder(String id);
    OrderApply findOne(String id);
    List<OrderApply> findAllByMonth(String month);
    List<OrderApply> findAllByDepartment(String department);
}
