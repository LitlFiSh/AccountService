package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Menu;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Litl_FiSh
 * @Date 2021/3/5 9:54
 */
@RestController
@RequestMapping("/admin")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/menu")
    public JsonResult addMenu(HttpServletRequest request, @Validated @RequestBody Menu menu){
        menuService.addMenu(menu);
        return ResultTool.success();
    }

    @PutMapping("/menu")
    public JsonResult updateMenu(HttpServletRequest request, @Validated @RequestBody Menu menu){
        menuService.updateMenu(menu);
        return ResultTool.success();
    }

    @DeleteMapping("/menu/{id}")
    public JsonResult deleteMenu(HttpServletRequest request, @PathVariable(value = "id") Integer id){
        menuService.deleteMenu(id);
        return ResultTool.success();
    }

    @GetMapping("/menu/menus")
    public JsonResult getAllMenu(){
        return ResultTool.success(menuService.findAll());
    }
}
