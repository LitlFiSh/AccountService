package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderApplyService {
    void addOrder(OrderApply orderApply);
    void updateOrder(OrderApply orderApply);
    void deleteOrder(String id);
    OrderApply findOne(String id);
    OrderApply findByDepartmentAndMonth(String department, String month);
    Page<OrderApply> findAllByUser(String id, Integer page);
    List<OrderApply> findAllByMonth(String month);
    List<OrderApply> findAllByDepartment(String department);
    void generateFile(int type);
}
