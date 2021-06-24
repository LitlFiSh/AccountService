package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.PurchaceOrder;
import org.springframework.data.domain.Page;

/**
 * @author Litl_FiSh
 * @Date 2021/3/26 22:09
 */
public interface PurchaceOrderService {
    void createPurchace(PurchaceOrder purchaceOrder);
    void updatePurchace(PurchaceOrder purchaceOrder);
    void deletePurchace(Integer id);
    PurchaceOrder findOne(Integer id);

    Page<PurchaceOrder> findAllByUser(String uid, Integer page);
    Page<PurchaceOrder> findAll(Integer page);
}
