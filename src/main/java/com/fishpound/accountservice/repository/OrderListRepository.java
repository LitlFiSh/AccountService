package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderList, String> {
    OrderList getById(String id);
    Long countAllByOrOrderApply_Id(String oid);
    Page<OrderList> findAllByStatus(Integer status, Pageable pageable);
//    List<OrderList> findAllByOrderApply_IdAnAndStatusNot(String id, Integer status);
//    List<OrderList> findAllByIdAndStatusNot(String id, Integer status);
}
