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
    @Query(value = "select * from orderlist where status = ?1 and purchace_id is null", nativeQuery = true)
    Page<OrderList> findAllByStatusAndPurchaceId(Integer status, Pageable pageable);

    @Query(value = "select * from orderapply where id like ?1" +
            " and apply_department like ?2" +
            " and apply_date > ?3" +
            " and apply_date < ?4" +
            " and apply_user like ?5" +
            " and fund_code like ?6" +
            " and status != ?7"
            , nativeQuery = true)
    Page<OrderList> findInCondition(String id, String deptName, String startDate, String endDate,
                                     String username, String fundcode, Integer status, Pageable pageable);
}
