package com.fishpound.accountservice.result;

import com.fishpound.accountservice.entity.Role;

import java.util.List;

public class ResultUser {
    private Integer id;
    private String username;
    private String department;
    private String password;
    private List<String> roles;

    public ResultUser() {
    }

    public ResultUser(Integer id, String username, String department, List<String> roles) {
        this.id = id;
        this.username = username;
        this.department = department;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
