package com.fishpound.accountservice;

import com.alibaba.fastjson.JSON;
import com.fishpound.accountservice.entity.*;
import com.fishpound.accountservice.repository.DepartmentRepository;
import com.fishpound.accountservice.repository.MenuRepository;
import com.fishpound.accountservice.repository.OrderApplyRepository;
import com.fishpound.accountservice.repository.RoleRepository;
import com.fishpound.accountservice.result.ResultMenu;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.service.AccountService;
import com.fishpound.accountservice.service.FileGenerator;
import com.fishpound.accountservice.service.RoleService;
import com.fishpound.accountservice.service.UserInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

@SpringBootTest
class AccountserviceApplicationTests {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AccountService accountService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    OrderApplyRepository orderApplyRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void passwordEncoded(){
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

//    @Test
//    void databaseTest(){
//        //用用户名查询出用户的全部信息 account表 userinfo表 角色信息以及部门信息
//        UserInfo user01 = userInfoService.findByUsername("admin01");
//        System.out.println(user01.getUsername());
//        System.out.println(user01.getAccount().getPassword());
//        System.out.println(user01.getDepartment().getDeptName());
//        List<Role> roles = user01.getAccount().getRoles();
//        for(Role r : roles){
//            System.out.println(r.getRoleName());
//        }
//    }

//    @Test
//    void findRole(){
//        List<String> roleName = new ArrayList<>();
//        roleName.add("USER");
//        roleName.add("ADMIN");
//        for(String name : roleName){
//            System.out.println(roleRepository.findByRoleName(name).getId());
//        }
//    }

//    @Test
//    void addUser(){
//        List<String> roleName = new ArrayList<>();
//        roleName.add("USER");
//        ResultUser user = new ResultUser(3, "testman", "部门1",
//                bCryptPasswordEncoder.encode("123456"), roleName);
//        UserInfo userInfo = new UserInfo();
//        Account account = new Account();
//        List<Role> roles = new ArrayList<>();
//        for(String name : roleName) {
//            Role role = roleRepository.findByRoleName(name);
//            roles.add(role);
//        }
//        System.out.println(roles);
//        account.setId(user.getId());
//        account.setPassword(user.getPassword());
//        account.setRoles(roles);
//        account.setActive(true);
//
//        userInfo.setUsername(user.getUsername());
//        userInfo.setId(user.getId());
//        userInfo.setAccount(account);
//        userInfo.setDepartment(departmentRepository.findByDeptName(user.getDepartment()));
//
////        accountService.save(account);
//        userInfoService.save(userInfo);
//    }

//    @Test
//    void deleteUser(){
//        UserInfo user = userInfoService.findByUsername("testman");
//        userInfoService.delete(user);
//    }

//    @Test
//    void addMenu(){
//        Role roleUser = roleRepository.findByRoleName("USER");
//        Role roleAdmin = roleRepository.findByRoleName("ADMIN");
//
//        List<Role> userList = new ArrayList<>();
//        userList.add(roleUser);
//
//        List<Role> adminList = new ArrayList<>();
//        adminList.add(roleUser);
//        adminList.add(roleAdmin);
//
////        Menu menu1 = new Menu();
////        menu1.setId(1);
////        menu1.setName("主菜单1");
////        menu1.setPath("/main1");
////        menu1.setRolesmenu(userList);
////
////        menuRepository.save(menu1);
//
//        Menu CMenu1 = new Menu();
//        Menu CMenu2 = new Menu();
//        CMenu1.setId(11);
//        CMenu2.setId(12);
//        CMenu1.setName("子菜单1");
//        CMenu2.setName("子菜单2");
//        CMenu1.setPath("/main1/c1");
//        CMenu2.setPath("/main1/c2");
//        CMenu1.setPid(1);
//        CMenu2.setPid(1);
//
////        menuRepository.save(CMenu1);
////        menuRepository.save(CMenu2);
//    }

//    @Test
//    void findMenu(){
//        Menu menu = menuRepository.findById(1).get();
//        System.out.println(menu.getId());
//        System.out.println(menu.getName());
//        System.out.println(menu.getPath());
//        System.out.println(JSON.toJSONString(menu.getChildren()));
//    }

//    @Test
//    void findMenuByRole(){
//        Role role = roleRepository.findByRoleName("USER");
//        List<Menu> menus = role.getMenus();
//        List<ResultMenu> resultMenus = new ArrayList<>();
//        for(Menu menu : menus){
//            resultMenus.add(new ResultMenu(menu.getName(), menu.getPath(), menu.getChildren()));
//        }
//        System.out.println(resultMenus);
//    }

    @Test
    void fileTest() throws FileNotFoundException {
        try{
            OrderApply orderApply = orderApplyRepository.getById("20201101");
            System.out.println(orderApply.getApplyDepartment());
            System.out.println(orderApply.getOrderLists());
            System.out.println(orderApply.getId());
            System.out.println(orderApply.getApplyDate());
            FileOutputStream stream = new FileOutputStream("G:\\作业\\作业\\四\\项目\\new.xls");
            HSSFWorkbook workbook = FileGenerator.generateExcel(orderApply, true);
            workbook.write(stream);
            stream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
