package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.entity.PurchaceOrder;
import com.fishpound.accountservice.entity.Settings;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderListService;
import com.fishpound.accountservice.service.PurchaceOrderService;
import com.fishpound.accountservice.service.SettingsService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Litl_FiSh
 * @Date 2021/3/26 22:40
 */
@RestController
@RequestMapping("/purchace")
public class PurchaceOrderController {
    @Autowired
    private PurchaceOrderService purchaceOrderService;
    @Autowired
    private OrderListService orderListService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 建立一个新的采购单
     * @param requestMap
     * @return
     */
    @PostMapping("")
    public JsonResult createPurchace(@RequestBody Map<String, Object> requestMap){
        List<String> lists = (List<String>) requestMap.get("orderLists");
        String uid = requestMap.get("uid").toString();
        if(lists.size() == 0){
            return ResultTool.fail("设备列表id为空");
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
            if(o.getPurchaceOrder() == null) {
                orderLists.add(o);
            }
        }
        purchaceOrder.setOrderLists(orderLists);
        purchaceOrder.setStatus(4);
        purchaceOrder.setUid(uid);
        purchaceOrder.setCreateTime(new Date());
        purchaceOrder.setUpdateTime(new Date());
        purchaceOrderService.createPurchace(purchaceOrder);

        Settings settings = settingsService.findByDescription(uid);
        if(settings == null){
            Settings s = new Settings();
            s.setDescription(uid);
            s.setValue("1");
            settingsService.addSetting(s);
        } else{
            if("0".equals(settings.getValue())) {
                settings.setValue("1");
                settingsService.updateSettings(settings);
            }
        }

        return ResultTool.success();
    }

    /**
     * 获取该用户所能看到的所有申请单
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/purchaces")
    public JsonResult getUsersPurchace(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       HttpServletRequest request)
    {
        String uid = request.getAttribute("user").toString();
        Settings s = settingsService.findByDescription(uid);
        UserInfo user = userInfoService.findById(uid);
        if(user.getAccount().getRole().getId() == 1){
            return ResultTool.success(purchaceOrderService.findAll(page));
        } else{
            if(s != null && "2".equals(s.getValue())) {
                return ResultTool.success(purchaceOrderService.findAll(page));
            } else if(s != null && "1".equals(s.getValue())){
                return ResultTool.success(purchaceOrderService.findAllByUser(uid, page));
            } else{
                return ResultTool.fail("没有权限");
            }
        }
    }
}
