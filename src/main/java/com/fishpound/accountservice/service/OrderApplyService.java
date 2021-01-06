package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface OrderApplyService {
    void addOrder(OrderApply orderApply);
    void updateOrder(OrderApply orderApply);
    void deleteOrder(String id);
    OrderApply findOne(String id);
    Page<OrderApply> findByDepartmentAndMonth(String department, Date date, Integer page);
    Page<OrderApply> findByUser(String id, Integer page);
    Page<OrderApply> findByMonth(Date month, Integer page);
    Page<OrderApply> findByDepartment(String department, Integer page);
    Page<OrderApply> findDeleted(Integer page);
    void uploadFile(String id, byte[] data);
}
