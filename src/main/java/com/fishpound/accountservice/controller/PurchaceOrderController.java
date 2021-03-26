package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.entity.PurchaceOrder;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderListService;
import com.fishpound.accountservice.service.PurchaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Litl_FiSh
 * @Date 2021/3/26 22:40
 */
@RestController
@RequestMapping("/admin/purchace")
public class PurchaceOrderController {
    @Autowired
    PurchaceOrderService purchaceOrderService;
    @Autowired
    OrderListService orderListService;

    @PostMapping("")
    public JsonResult createPurchace(@RequestBody Map<String, Object> requestMap){
        List<String> lists = (List<String>) requestMap.get("orderLists");
        String uid = requestMap.get("uid").toString();
        if(lists.size() == 0){
            return ResultTool.fail("设备列表为空");
        }
        if(uid == null || "".equals(uid)){
            return ResultTool.fail("用户id为空");
        }
        PurchaceOrder purchaceOrder = new PurchaceOrder();
        List<OrderList> orderLists = new ArrayList<>();
        for(String olid : lists){
            OrderList o = orderListService.getOne(olid);
            if(o == null){
                return ResultTool.fail("找不到id为" + olid + "的设备列表");
            }
            if(o.getStatus() != 3){
                return ResultTool.fail("设备列表状态错误，不可以对其进行分配采购单操作");
            }
            orderLists.add(o);
        }
        purchaceOrder.setOrderLists(orderLists);
        purchaceOrder.setStatus(4);
        purchaceOrder.setUid(uid);
        purchaceOrderService.createPurchace(purchaceOrder);
        return ResultTool.success();
    }
}
