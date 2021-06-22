package com.fishpound.accountservice.result;

import com.fishpound.accountservice.entity.UserInfo;

/**
 * 用于用户信息的返回以及添加用户时前端传入参数的对应
 */
public class ResultUser {
    private String id;
    private String username;
    private String department;
    private String password;
    private String role;
    private String inCharge;

    public ResultUser() {
    }

    public ResultUser(String id, String username, String department, String password, String role, String inCharge) {
        this.id = id;
        this.username = username;
        this.department = department;
        this.password = password;
        this.role = role;
        this.inCharge = inCharge;
    }

    public ResultUser(String id, String username, String department, String role, String inCharge) {
        this.id = id;
        this.username = username;
        this.department = department;
        this.role = role;
        this.inCharge = inCharge;
    }

    public ResultUser(UserInfo userInfo, boolean hideSensitive) {
        if(hideSensitive){
            //不返回敏感信息
            this.id = userInfo.getId();
            this.username = userInfo.getUsername();
            this.department = userInfo.getDepartment().getDeptName();
        } else {
            this.id = userInfo.getId();
            this.username = userInfo.getUsername();
            this.department = userInfo.getDepartment().getDeptName();
            this.role = userInfo.getAccount().getRole().getRoleDescription();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInCharge() {
        return inCharge;
    }

    public void setInCharge(String inCharge) {
        this.inCharge = inCharge;
    }
}
