package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderList, String> {
    OrderList getById(String id);
    Long countAllByOrOrderApply_Id(String oid);
    Page<OrderList> findAllByStatus(Integer status, Pageable pageable);
    @Query(value = "select * from orderlist where id like ?1" +
            " and name like ?2" +
            " and type > ?3" +
            " and configuration < ?4" +
            " and from_id = ?5" +
            " and status = ?6" +
            " and purchace_id is null"
            , nativeQuery = true)
    Page<OrderList> findInConditionAndPurchaceId(String id, String name, String type, String configuration,
                                                 String from_id, Integer status, Pageable pageable);

    @Query(value = "select * from orderlist where id like ?1" +
            " and name like ?2" +
            " and type > ?3" +
            " and configuration < ?4" +
            " and from_id = ?5"
            , nativeQuery = true)
    Page<OrderList> findInCondition(String id, String name, String type, String configuration,
                                     String from_id, Pageable pageable);
}
