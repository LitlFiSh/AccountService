package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.*;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @Autowired
    private FileService fileService;

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

    /**
     * 获取采购单自定义状态
     * @param pid
     * @return
     */
    @GetMapping("/purchaceStatus")
    public JsonResult getStatus(@RequestParam(value = "pid")Integer pid){
        Map<String, Object> result = new LinkedHashMap<>();
        List<File> all = fileService.findAllByPurchaceId(pid);
        List<Settings> status = settingsService.fingAllByDescription("采购单状态");
        if(status == null || status.size() == 0){
            return ResultTool.fail("还没有设置采购单状态");
        }
        if(all == null || all.size() == 0){
            for(Settings s : status){
                boolean flag = false;
                result.put(s.getValue(), flag);
            }
            return ResultTool.success(result);
        }
        File file1 = all.get(0);
        boolean b = false;
        for(Settings s : status){
            if(file1.getDescription().equals(s.getValue())){
                b = true;
            }
        }
        if(b){
            //其中一个状态符合设置中的状态
            for(Settings s : status){
                boolean flag = false;
                for(File f1 : all){
                    if(s.getValue().equals(f1.getDescription())){
                        flag = true;
                    }
                }
                result.put(s.getValue(), flag);
            }
        } else{
            for(File f2 : all){
                result.put(f2.getDescription(), true);
            }
        }
        return ResultTool.success(result);
    }
}
