package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderApplyRepository extends JpaRepository<OrderApply, String> {
    OrderApply getById(String id);
    OrderApply findByIdLike(String id);
    OrderApply findByApplyDepartmentAndIdStartsWith(String department, String preId);
    Page<OrderApply> findAllByApplyUser(Integer userId, Pageable pageable);
}
