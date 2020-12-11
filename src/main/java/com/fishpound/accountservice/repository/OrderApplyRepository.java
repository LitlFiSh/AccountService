package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderApplyRepository extends JpaRepository<OrderApply, String> {
    OrderApply getById(String id);
    List<OrderApply> findAllByStatusIsNot(Integer status);
    List<OrderApply> findAllByIdStartsWith(String id);
    long countByIdStartsWith(String id);
    List<OrderApply> findAllByApplyUserAndStatusNotLike(String username, Integer status);
    Page<OrderApply> findAllByUidAndStatusNot(String username, Integer status, Pageable pageable);
}
