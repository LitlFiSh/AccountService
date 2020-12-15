package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface OrderApplyService {
    void addOrder(OrderApply orderApply);
    void updateOrder(OrderApply orderApply);
    void deleteOrder(String id);
    OrderApply findOne(String id);
    Page<OrderApply> findByDepartmentAndMonth(String department, Date date, Integer page);
    Page<OrderApply> findAllByUser(String id, Integer page);
    Page<OrderApply> findAllByMonth(Date month, Integer page);
    Page<OrderApply> findAllByDepartment(String department, Integer page);
    void generateFile(int type);
}
