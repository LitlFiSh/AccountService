package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.OrderApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderApplyRepository extends JpaRepository<OrderApply, String> {
    OrderApply getById(String id);
    long countByIdStartsWith(String id);
    Page<OrderApply> findAllByUidAndStatusNot(String username, Integer status, Pageable pageable);

    @Query(value ="select * from orderapply where uid = ?1" +
            " and id like ?2" +
            " and apply_department like ?3" +
            " and apply_date > ?4" +
            " and apply_date < ?5" +
            " and apply_user like ?6" +
            " and fund_code like ?7" +
            " and status != ?8" +
            " order by apply_date desc",
            nativeQuery = true)
    Page<OrderApply> findUserOrders1(String uid, String id, String deptName, String startDate, String endDate,
                                    String username, String fundcode, Integer status, Pageable pageable);
    @Query(value ="select * from orderapply where uid = ?1" +
            " and id like ?2" +
            " and apply_department like ?3" +
            " and apply_date > ?4" +
            " and apply_date < ?5" +
            " and apply_user like ?6" +
            " and fund_code like ?7" +
            " and status = ?8" +
            " order by apply_date desc",
            nativeQuery = true)
    Page<OrderApply> findUserOrders2(String uid, String id, String deptName, String startDate, String endDate,
                                    String username, String fundcode, Integer status, Pageable pageable);


    Page<OrderApply> findAllByApplyDateBetweenAndStatusNot(Date date1, Date date2, Integer status, Pageable pageable);
    Page<OrderApply> findAllByApplyDepartmentAndApplyDateBetweenAndStatusNot(
            String department, Date date1, Date date2, Integer status, Pageable pageable);
    Page<OrderApply> findAlllByApplyDepartmentAndStatusNot(String department, Integer status, Pageable pageable);
    Page<OrderApply> findAllByStatus(Integer status, Pageable pageable);
    Page<OrderApply> findAllByApplyDepartmentAndStatus(String department, Integer status, Pageable pageable);

    List<OrderApply> findAllByApplyDate(Date date);
    List<OrderApply> findAllByApplyDateBefore(Date date);
    List<OrderApply> findAllByApplyDepartmentAndApplyDateBetween(String department, Date date1, Date date2);

    @Query(value = "select * from orderapply where id like ?1" +
            " and apply_department like ?2" +
            " and apply_date > ?3" +
            " and apply_date < ?4" +
            " and apply_user like ?5" +
            " and fund_code like ?6" +
            " and status != ?7"
            , nativeQuery = true)
    Page<OrderApply> findInCondition(String id, String deptName, String startDate, String endDate,
                                     String username, String fundcode, Integer status, Pageable pageable);
}
