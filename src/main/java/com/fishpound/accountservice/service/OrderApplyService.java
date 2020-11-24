package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;

public interface OrderApplyService {
    void addOrder(OrderApply orderApply);
    void updateOrder(OrderApply orderApply);
    void deleteOrder(Integer id);
    OrderApply findOne(Integer id);
    Page<OrderApply> findAll(String username, Integer page);
}
