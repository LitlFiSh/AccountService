package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.repository.OrderApplyRepository;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.OrderApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderApplyServiceImpl implements OrderApplyService {
    @Autowired
    OrderApplyRepository orderApplyRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public void addOrder(OrderApply orderApply) {
        orderApplyRepository.save(orderApply);
    }

    @Override
    public void updateOrder(OrderApply orderApply) {
        orderApplyRepository.save(orderApply);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderApplyRepository.deleteById(id);
    }

    @Override
    public OrderApply findOne(Integer id) {
        return orderApplyRepository.getOne(id);
    }

    @Override
    public Page<OrderApply> findAll(String username, Integer page) {
        if(page == null){
            page = 1;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        UserInfo user = userInfoRepository.findByUsername(username);
        return orderApplyRepository.findAllByApplyUser(user.getId(), pageable);
    }
}
