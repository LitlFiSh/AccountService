package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.File;
import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.entity.Settings;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.FileService;
import com.fishpound.accountservice.service.OrderListService;
import com.fishpound.accountservice.service.SettingsService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderlist")
public class OrderListController {
    @Autowired
    OrderListService orderListService;
    @Autowired
    FileService fileService;
    @Autowired
    SettingsService settingsService;
    @Autowired
    private UserInfoService userInfoService;

//    @DeleteMapping("/{id}")
//    public JsonResult deleteOrderList(@PathVariable(value = "id") String id){
//        if(orderListService.deleteOrderList(id)){
//            return ResultTool.success();
//        } else{
//            return ResultTool.fail();
//        }
//    }
    @GetMapping("/status")
    public JsonResult getStatus(@RequestParam(value = "id") String id){
        OrderList orderList = orderListService.getOne(id);
        Integer pid = orderList.getPurchaceOrder().getId();
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

    @GetMapping("/all")
    public JsonResult findAll(@RequestParam(value = "id", defaultValue = "%") String id,
                              @RequestParam(value = "name", defaultValue = "%") String name,
                              @RequestParam(value = "type", defaultValue = "%") String type,
                              @RequestParam(value = "configuration", defaultValue = "%") String configuration,
                              @RequestParam(value = "applyUid", defaultValue = "%") String uid,
                              @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        Map<String, Object> params = new HashMap<>();
        if (!"%".equals(id)) {
            id = "%" + id + "%";
        }
        if (!"%".equals(name)) {
            name = "%" + name + "%";
        }
        if (!"%".equals(type)) {
            type = "%" + type + "%";
        }
        if (!"%".equals(configuration)) {
            configuration = "%" + configuration + "%";
        }
        if (!"%".equals(uid)) {
            UserInfo user = userInfoService.findById(uid);
            uid = user.getId();
        }
        params.put("id", id);
        params.put("name", name);
        params.put("type", type);
        params.put("configuration", configuration);
        params.put("uid", uid);
        params.put("status", 3);
        Map<String, Object> resultMap = orderListService.findAll(params, page);
        return ResultTool.success(resultMap);
    }

    @GetMapping("/orderlists/all")
    public JsonResult getAllOrderList(@RequestParam(value = "id", defaultValue = "%") String id,
                                      @RequestParam(value = "name", defaultValue = "%") String name,
                                      @RequestParam(value = "type", defaultValue = "%") String type,
                                      @RequestParam(value = "configuration", defaultValue = "%") String configuration,
                                      @RequestParam(value = "applyUid", defaultValue = "%") String uid,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        Map<String, Object> params = new HashMap<>();
        if (!"%".equals(id)) {
            id = "%" + id + "%";
        }
        if (!"%".equals(name)) {
            name = "%" + name + "%";
        }
        if (!"%".equals(type)) {
            type = "%" + type + "%";
        }
        if (!"%".equals(configuration)) {
            configuration = "%" + configuration + "%";
        }
        if (!"%".equals(uid)) {
            UserInfo user = userInfoService.findById(uid);
            uid = user.getId();
        }
        params.put("id", id);
        params.put("name", name);
        params.put("type", type);
        params.put("configuration", configuration);
        params.put("uid", uid);
        Map<String, Object> resultMap = orderListService.findAll(params, page);
        return ResultTool.success(resultMap);
    }
}
