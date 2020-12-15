package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.repository.DepartmentRepository;
import com.fishpound.accountservice.repository.OrderApplyRepository;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.tools.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderApplyServiceImpl implements OrderApplyService {
    @Autowired
    OrderApplyRepository orderApplyRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * 新增申请单
     * @param orderApply
     */
    @Override
    public void addOrder(OrderApply orderApply) {
        String idPrefix = "", id;
        //id 生成策略：年份(4) 部门编号(2) 部门该年第 n 份申请(2)
        Calendar now = Calendar.getInstance();
        idPrefix = "" + now.get(Calendar.YEAR) + orderApply.getApplyDepartment();
        id = idPrefix + new DecimalFormat("00").format(orderApplyRepository.countByIdStartsWith(idPrefix) + 1);
//        System.out.println(id);
        orderApply.setId(id);
        List<OrderList> orderLists = orderApply.getOrderLists();
        for(OrderList orderList : orderLists){
            orderList.setId(id + orderList.getNo());
            orderList.setOrderApply(orderApply);
        }
        orderApply.setOrderLists(orderLists);
        orderApply.setStatus(1);
        orderApplyRepository.save(orderApply);
    }

    /**
     * 更新申请单
     * @param orderApply
     */
    @Override
    public void updateOrder(OrderApply orderApply) {
        orderApplyRepository.save(orderApply);
    }

    /**
     * 删除申请单
     * 删除：将申请单状态设置为已删除（-1）
     * @param id
     */
    @Override
    public void deleteOrder(String id) {
//        orderApplyRepository.deleteById(id);
        OrderApply orderApply = orderApplyRepository.getById(id);
        //获取该申请单包含的申请列表，并将申请列表的状态设置为已删除（-1）
        List<OrderList> orderLists = orderApply.getOrderLists();
        for(OrderList orderList : orderLists){
            orderList.setStatus(-1);
        }
        orderApply.setOrderLists(orderLists);
        orderApply.setStatus(-1);
        orderApplyRepository.save(orderApply);
    }

    /**
     * 查找编号为 id 的申请单
     * @param id
     * @return
     */
    @Override
    public OrderApply findOne(String id) {
        return orderApplyRepository.getById(id);
    }

    /**
     * 查询某部门某月的所有申请单
     * @param department
     * @param date
     * @param page
     * @return
     */
    @Override
    public Page<OrderApply> findByDepartmentAndMonth(String department, Date date, Integer page) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        try {
            Date last = format.parse(year + "-" + (month - 1) + "-" + "31");
            Date next = format.parse(year + "-" + (month + 1) + "-" + "00");
            PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
            return orderApplyRepository.findAllByApplyDepartmentAndApplyDateBetweenAndStatusNot(
                    department, last, next, -1, pageTools.sortSingle());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<OrderApply> findAllByUser(String id, Integer page) {
        PageTools pageTools = new PageTools("applyDate", Sort.Direction.DESC, page);
        return orderApplyRepository.findAllByUidAndStatusNot(id, -1, pageTools.sortSingle());
    }

    /**
     * 查询月份为 month 的所有申请单
     * @param date
     * @return
     */
    @Override
    public Page<OrderApply> findAllByMonth(Date date, Integer page) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        try {
            Date last = format.parse(year + "-" + (month - 1) + "-" + "31");
            Date next = format.parse(year + "-" + (month + 1) + "-" + "00");
            PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
            return orderApplyRepository.findAllByApplyDateBetweenAndStatusNot(last, next, -1, pageTools.sortSingle());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询部门编号为 department 的申请单
     * @param department
     * @return
     */
    @Override
    public Page<OrderApply> findAllByDepartment(String department, Integer page) {
        return null;
    }

    @Override
    public void generateFile(int type) {
        switch(type){
            case 1:
            case 2:
            default:
                break;
        }
    }
}
