package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.repository.OrderListRepository;
import com.fishpound.accountservice.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderListServiceImpl implements OrderListService {
    @Autowired
    OrderListRepository orderListRepository;

    @Override
    public List<OrderList> findAllByUserId(String id) {
        List<OrderList> orderLists = orderListRepository.findAllByUserId(id);
        return orderLists;
    }
}
