package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.*;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 17:03
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private PurchaceOrderService purchaceOrderService;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/upload")
    public JsonResult uploadFile(@RequestParam(value = "file", required = false)MultipartFile file,
                                 @RequestParam(value = "pid")Integer pid,
                                 @RequestParam(value = "status")String status,
                                 HttpServletRequest request)
    {
        PurchaceOrder purchaceOrder = purchaceOrderService.findOne(pid);
        String uid = (String) request.getAttribute("user");
        UserInfo user = userInfoService.findById(uid);
        Settings setting = settingsService.findByDescription(uid);
        if(user.getAccount().getRole().getId() != 1){
            if(setting == null || "0".equals(setting.getValue()) || "1".equals(setting.getValue())){
                return ResultTool.fail("没有权限");
            }
        }
        try {
            File f = new File();
            if (file != null) {
                byte[] data = file.getBytes();
                f.setFile(data);
            }
            f.setDescription(status);
            f.setPurchaceId(pid);
            f.setCreateTime(new Date());
            f.setUpdateTime(new Date());
            fileService.addFile(f);
        } catch (Exception e) {
            return ResultTool.fail(e.getMessage());
        }
        return ResultTool.success();
    }

    @GetMapping()
    public void getFile(@RequestParam(value = "pid")Integer pid, @RequestParam(value = "description") String description, HttpServletResponse response){
        File file = fileService.findAllByPurchaceIdAndDesc(pid, description);
        try{
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(file.getFile());
            outputStream.flush();
            outputStream.close();
        } catch(Exception e){

        }
    }
}
