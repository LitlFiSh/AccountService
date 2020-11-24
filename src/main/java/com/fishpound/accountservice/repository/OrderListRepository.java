package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderListRepository extends JpaRepository<OrderList, Integer> {
}
