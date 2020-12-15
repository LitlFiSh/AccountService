package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderApplyRepository extends JpaRepository<OrderApply, String> {
    OrderApply getById(String id);
    long countByIdStartsWith(String id);
    Page<OrderApply> findAllByUidAndStatusNot(String username, Integer status, Pageable pageable);
    Page<OrderApply> findAllByApplyDateBetweenAndStatusNot(Date date1, Date date2, Integer status, Pageable pageable);
    Page<OrderApply> findAllByApplyDepartmentAndApplyDateBetweenAndStatusNot(
            String department, Date date1, Date date2, Integer status, Pageable pageable);

    List<OrderApply> findAllByApplyDate(Date date);
    List<OrderApply> findAllByApplyDateBefore(Date date);
    List<OrderApply> findAllByApplyDepartmentAndApplyDateBetween(String department, Date date1, Date date2);
}
