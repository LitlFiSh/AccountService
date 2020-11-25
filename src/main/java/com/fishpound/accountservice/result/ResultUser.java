package com.fishpound.accountservice.result;

/**
 * 用于用户信息的返回以及添加用户时前端传入参数的对应
 */
public class ResultUser {
    private String id;
    private String username;
    private String department;
    private String password;
    private Integer role;

    public ResultUser() {
    }

    public ResultUser(String id, String username, String department, Integer role) {
        this.id = id;
        this.username = username;
        this.department = department;
        this.role = role;
    }

    public ResultUser(String id, String username, String department, String password, Integer role) {
        this.id = id;
        this.username = username;
        this.department = department;
        this.password = password;
        this.role = role;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
