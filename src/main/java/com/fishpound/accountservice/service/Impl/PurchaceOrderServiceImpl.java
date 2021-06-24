package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.PurchaceOrder;
import com.fishpound.accountservice.repository.PurchaceOrderRepository;
import com.fishpound.accountservice.service.PurchaceOrderService;
import com.fishpound.accountservice.service.tools.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Litl_FiSh
 * @Date 2021/3/26 22:34
 */
@Service
public class PurchaceOrderServiceImpl implements PurchaceOrderService {
    @Autowired
    PurchaceOrderRepository purchaceOrderRepository;

    @Override
    public void createPurchace(PurchaceOrder purchaceOrder) {
        purchaceOrderRepository.save(purchaceOrder);
    }

    @Override
    public void updatePurchace(PurchaceOrder purchaceOrder) {
        purchaceOrderRepository.save(purchaceOrder);
    }

    @Override
    public void deletePurchace(Integer id) {
        purchaceOrderRepository.deleteById(id);
    }

    @Override
    public PurchaceOrder findOne(Integer id) {
        return purchaceOrderRepository.getOne(id);
    }

    @Override
    public Page<PurchaceOrder> findAllByUser(String uid, Integer page) {
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        return purchaceOrderRepository.findAllByUid(uid, pageTools.sortSingle());
    }

    @Override
    public Page<PurchaceOrder> findAll(Integer page) {
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        return purchaceOrderRepository.findAll(pageTools.sortSingle());
    }
}
