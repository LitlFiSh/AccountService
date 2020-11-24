package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApplyRepository extends JpaRepository<OrderApply, Integer> {
    Page<OrderApply> findAllByApplyUser(Integer id, Pageable pageable);
}
