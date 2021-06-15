package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Settings;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Litl_FiSh
 * @Date 2021/3/21 10:19
 */
@RestController
@RequestMapping("/admin/settings")
public class SettingsController {
    @Autowired
    SettingsService settingsService;

    @PostMapping()
    public JsonResult addSettings(@RequestBody Settings settings){
        settingsService.addSetting(settings);
        return ResultTool.success();
    }

    @PutMapping()
    public JsonResult updateSettings(@RequestBody Settings settings){
        settingsService.updateSettings(settings);
        return ResultTool.success();
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteSettings(@PathVariable Integer id){
        settingsService.deleteSettings(id);
        return ResultTool.success();
    }

    @GetMapping()
    public JsonResult getSettings(@RequestParam(value = "description") String desc){
        return ResultTool.success(settingsService.findByDescription(desc));
    }

    @GetMapping("/all")
    public JsonResult getAll(@RequestParam(value = "description") String desc){
        return ResultTool.success(settingsService.findByDescription(desc));
    }
}
