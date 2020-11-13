package com.fishpound.accountservice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 与数据库对应的账户实体
 * id：主键
 * openID：暂时留空，用于以后微信登录
 */
@Entity
@Table(name="account")
public class Account {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "openid")
    private String openID;

    @ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
