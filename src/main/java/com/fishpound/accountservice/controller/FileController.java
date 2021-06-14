package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.File;
import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.Settings;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 17:03
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private OrderApplyService orderApplyService;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/upload")
    public JsonResult uploadFile(@RequestParam(value = "file")MultipartFile file,
                                 @RequestParam(value = "oid")String oid,
                                 @RequestParam(value = "status")String status,
                                 HttpServletRequest request)
    {
        OrderApply orderApply = orderApplyService.findOne(oid);
        if(orderApply.getStatus() == 3) {
            String uid = (String) request.getAttribute("user");
            UserInfo user = userInfoService.findById(uid);
            Settings setting = settingsService.findByDescription(uid);
            if(!"管理员".equals(user.getAccount().getRole().getRoleName()) && (setting == null || "0".equals(setting.getValue()) || "1".equals(setting.getValue()))){
                return ResultTool.fail("没有权限");
            }
            try {
                File f = new File();
                if (file != null) {
                    byte[] data = file.getBytes();
                    f.setFile(data);
                }
                f.setDescription(status);
                f.setOid(oid);
                f.setCreateTime(new Date());
                f.setUpdateTime(new Date());
                fileService.addFile(f);
            } catch (Exception e) {
                return ResultTool.fail(e.getMessage());
            }
            return ResultTool.success();
        } else{
            return ResultTool.fail("申请单状态错误");
        }
    }
}
