package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.repository.OrderApplyRepository;
import com.fishpound.accountservice.repository.OrderListRepository;
import com.fishpound.accountservice.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderListServiceImpl implements OrderListService {
    @Autowired
    OrderListRepository orderListRepository;
    @Autowired
    OrderApplyRepository orderApplyRepository;

    @Override
    public OrderList getOne(String id) {
        return orderListRepository.getById(id);
    }

    @Override
    public List<OrderList> findAllByUserId(String id) {
//        List<OrderList> orderLists = orderListRepository.findAllByUserIdAndStatusNotLike(id, -1);
//        return orderLists;
        return null;
    }

    @Override
    public boolean deleteOrderList(String id) {
//        orderListRepository.deleteById(id);
//        OrderList orderList = orderListRepository.getById(id);
//        OrderApply orderApply = orderApplyRepository.getById(orderList.getOrderApply().getId());
//        orderApply.setTotal(orderApply.getTotal() - orderList.getBudgetTotalPrice());
//        orderApplyRepository.save(orderApply);
//        orderListRepository.save(orderList);
        return false;
    }

    @Override
    public boolean addOrderList(OrderList orderList) {

        return false;
    }
}
