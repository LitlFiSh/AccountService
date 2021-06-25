package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.repository.OrderApplyRepository;
import com.fishpound.accountservice.repository.OrderListRepository;
import com.fishpound.accountservice.result.ResultOrderList;
import com.fishpound.accountservice.service.OrderListService;
import com.fishpound.accountservice.service.UserInfoService;
import com.fishpound.accountservice.service.tools.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderListServiceImpl implements OrderListService {
    @Autowired
    OrderListRepository orderListRepository;
    @Autowired
    OrderApplyRepository orderApplyRepository;
    @Autowired
    UserInfoService userInfoService;

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
    public Map<String, Object> findAll(Map<String, Object> param, Integer page) {
        Map<String, Object> result = new HashMap<>();
        List<ResultOrderList> resultList = new ArrayList<>();
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        Page<OrderList> orderLists = orderListRepository.findInConditionAndPurchaceId(
                param.get("id").toString(),
                param.get("name").toString(),
                param.get("type").toString(),
                param.get("configuration").toString(),
                param.get("oid").toString(),
                Integer.valueOf(param.get("status").toString()),
                pageTools.sortSingle()
        );
        for(OrderList orderList : orderLists.getContent()){
                String uid = orderList.getOrderApply().getUid();
                String username = userInfoService.findById(uid).getUsername();
                String deptName = orderList.getOrderApply().getApplyDepartment();
                ResultOrderList resultOrderList = new ResultOrderList(orderList, username, deptName);
                resultList.add(resultOrderList);
        }
        result.put("totalPages", orderLists.getTotalPages());
        result.put("totalElements", orderLists.getTotalElements());
        result.put("size", orderLists.getSize());
        result.put("number", orderLists.getNumber());
        result.put("content", resultList);
        return result;
    }

    @Override
    public Map<String, Object> findAllAdmin(Map<String, Object> param, Integer page) {
        Map<String, Object> result = new HashMap<>();
        List<ResultOrderList> resultList = new ArrayList<>();
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        Page<OrderList> orderLists = orderListRepository.findInCondition(
                param.get("id").toString(),
                param.get("name").toString(),
                param.get("type").toString(),
                param.get("configuration").toString(),
                param.get("oid").toString(),
                pageTools.sortSingle()
        );
        for(OrderList orderList : orderLists.getContent()){
            String uid = orderList.getOrderApply().getUid();
            String username = userInfoService.findById(uid).getUsername();
            String deptName = orderList.getOrderApply().getApplyDepartment();
            ResultOrderList resultOrderList = new ResultOrderList(orderList, username, deptName);
            resultList.add(resultOrderList);
        }
        result.put("totalPages", orderLists.getTotalPages());
        result.put("totalElements", orderLists.getTotalElements());
        result.put("size", orderLists.getSize());
        result.put("number", orderLists.getNumber());
        result.put("content", resultList);
        return result;
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
