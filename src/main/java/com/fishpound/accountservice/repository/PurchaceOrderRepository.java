package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.PurchaceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Litl_FiSh
 * @Date 2021/3/26 22:08
 */
public interface PurchaceOrderRepository extends JpaRepository<PurchaceOrder, Integer> {
    Page<PurchaceOrder> findAllByUid(String uid, Pageable pageable);
}
