package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.*;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.service.*;
import com.fishpound.accountservice.service.tools.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RoleService roleService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    CacheService cacheService;
    @Autowired
    SettingsService settingsService;

    /**
     * 根据用户ID获取用户信息
     * @param uid 用户ID
     * @return
     */
    @GetMapping("/user")
    public JsonResult getUser(@RequestParam(value = "uid") String uid){
        UserInfo userInfo = userInfoService.findById(uid);
        ResultUser user;
        if(userInfo == null){
            return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        Settings settings = settingsService.findByDescription(uid);
        if(settings != null) {
            user = new ResultUser(userInfo.getId(),
                    userInfo.getUsername(),
                    userInfo.getDepartment().getDeptName(),
                    userInfo.getAccount().getRole().getRoleDescription(),
                    settings.getValue());
        } else{
            user = new ResultUser(userInfo.getId(),
                    userInfo.getUsername(),
                    userInfo.getDepartment().getDeptName(),
                    userInfo.getAccount().getRole().getRoleDescription(),
                    "0");
        }
        return ResultTool.success(user);
    }

    /**
     * 添加用户
     * @param resultUser
     * @return
     */
    @PostMapping("/user")
    public JsonResult addUser(@Validated @RequestBody ResultUser resultUser){
        UserInfo userInfo = createUser(resultUser, true);
        if(userInfo != null){
            userInfoService.save(userInfo);
            return ResultTool.success();
        } else{
            return ResultTool.fail("用户ID已存在");
        }
    }

    /**
     * 更新用户信息
     * @param resultUser
     * @return
     */
    @PutMapping("/user")
    public JsonResult updateUser(@Validated @RequestBody ResultUser resultUser){
        UserInfo userInfo = createUser(resultUser, false);
        userInfoService.save(userInfo);
        return ResultTool.success();
    }

    /**
     * 获取除当前用户外的所有用户
     * @param request
     * @param page
     * @return
     */
    @GetMapping("/user/users")
    public JsonResult getAllUser(HttpServletRequest request,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        return ResultTool.success(userInfoService.findAllExcept(request.getAttribute("user").toString(), page));
    }

    /**
     * 通过用户真实姓名模糊查找用户信息
     * @param request
     * @return
     */
    @GetMapping("/user/users/info")
    public JsonResult getAdmin(HttpServletRequest request, @RequestParam(value = "username", defaultValue = "%") String name){
        /*List<UserInfo> adminList = userInfoService.findUserByRole(1);
        List<ResultUser> resultUserList = new ArrayList<>();
        for(UserInfo u : adminList){
            ResultUser ru = new ResultUser(u);
            resultUserList.add(ru);
        }*/
        if(!"%".equals(name)){
            name = "%" + name + "%";
        }
        List<ResultUser> resultUserList = userInfoService.findUsername(name);
        return ResultTool.success(resultUserList);
    }

    /**
     * 批量导入用户，通过传递下载的模板文件
     * @param file 文件模板
     * @return
     */
    @PostMapping("/user/batchAdd")
    public JsonResult batchAdduser(@RequestParam(value = "file") MultipartFile file){
        List<Map> userList = FileTools.importExcel(file);
        return ResultTool.success(userInfoService.batchAddUser(userList));
    }

    /**
     * 删除一条申请单记录（将该申请单状态更改为-1）
     * @param id 申请单id
     * @return
     */
    @DeleteMapping("/order/{id}")
    public JsonResult deleteOrder(@PathVariable(value = "id") String id){
        OrderApply order = orderApplyService.findOne(id);
        if(order == null){
            return ResultTool.fail("找不到该申请单");
        }
        order.setStatus(-1);
        orderApplyService.updateOrder(order);
        return ResultTool.success();
    }

    /**
     * 获取所有已删除的申请单（status = -1）
     * @param page
     * @return
     */
    @GetMapping("/order/deleted")
    public JsonResult getDeletedOrder(@RequestParam(value = "page", defaultValue = "1") Integer page){
        return ResultTool.success(orderApplyService.findDeleted(page));
    }

    /**
     * 还原一个已删除的申请单
     * @param id 申请单id
     * @return
     */
    @PutMapping("/order/reduct/{id}")
    public JsonResult reductOrder(@PathVariable(value = "id") String id){
        OrderApply orderApply = orderApplyService.findOne(id);
        if(orderApply == null){
            return ResultTool.fail("找不到申请单");
        }
        orderApply.setStatus(1);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }

    /**
     * 下载用户导入模板文件
     * @param response
     */
    @GetMapping("/user/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("用户导入模板", "UTF-8") + ".xlsx");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            Resource resource = new ClassPathResource("docs/用户导入模板.xlsx");   //静态文件位置
            InputStream stream = resource.getInputStream();
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(404);
        }
    }

//    /**
//     * 使缓存中的用户 token 失效
//     * @param uid
//     * @return
//     */
//    @GetMapping("/invalidate")
//    public JsonResult invalidateToken(@RequestParam(value = "uid") String uid)
//    {
//        cacheService.setCacheValue("token", uid, "123");
//        return ResultTool.success();
//    }

    @GetMapping("/user/appointment")
    public JsonResult appointmentUser(@RequestParam(value = "uid") String uid, @RequestParam(value = "type") String type){
        if("0".equals(type) || "1".equals(type) || "2".equals(type)) {
            UserInfo user = userInfoService.findById(uid);
            Settings setting = settingsService.findByDescription(uid);
            if (user == null) {
                //查找不到用户
                return ResultTool.fail("找不到用户");
            }
            if(setting == null){
                setting = new Settings();
            }
            setting.setDescription(uid);
            setting.setValue(type);
            settingsService.addSetting(setting);
            return ResultTool.success();
        } else{
            return ResultTool.fail("tpye参数的值错误（应为0、 1 或 2）");
        }
    }

//    @GetMapping("/user/disappointment")
//    public JsonResult disappointment(@RequestParam(value = "uid") String uid){
//        Settings settings = settingsService.findByDescription(uid);
//        if(settings != null){
//            settings.setValue("0");
//            settingsService.updateSettings(settings);
//        }
//        return ResultTool.success();
//    }

    @PostMapping("/department")
    public JsonResult addDepartment(@RequestBody Department department){
        departmentService.addDept(department);
        return ResultTool.success();
    }

    @PutMapping("/department")
    public JsonResult updateDepartment(@RequestBody Department department){
        departmentService.updateDept(department);
        return ResultTool.success();
    }

    @DeleteMapping("/department/{id}")
    public JsonResult deleteDepartment(@PathVariable(value = "id") String id){
        departmentService.deleteDept(id);
        return ResultTool.success();
    }

    /**
     * 通过 ResultUser 类生成实体类 UserInfo
     * @param user ResultUser类
     * @param b 是否新建用户
     * @return 实体类UserInfo
     */
    private UserInfo createUser(ResultUser user, boolean b){
        UserInfo u = userInfoService.findById(user.getId());
        UserInfo userInfo;
        Account account;
        if(b && u != null) {
            return null;
        }
        if(b) {
            userInfo = new UserInfo();
            account = new Account();
        } else{
            userInfo = u;
            account = u.getAccount();
        }
        Department department = departmentService.findByDeptName(user.getDepartment());
        Role role = roleService.findByDescription(user.getRole());
        if(department == null || role == null){
            return null;
        }
        account.setId(user.getId());
        if(b || (user.getPassword() != null && !"".equals(user.getPassword()))) {
            account.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else{
            account.setPassword(u.getAccount().getPassword());
        }
        account.setActive(true);
        account.setRole(role);

        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setDepartment(department);
        userInfo.setAccount(account);

        return userInfo;
    }
}
