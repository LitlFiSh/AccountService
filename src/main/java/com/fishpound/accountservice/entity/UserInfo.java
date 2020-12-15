package com.fishpound.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * 与数据库对应的用户信息实体
 */
@Entity
@Table(name = "userInfo")
public class UserInfo {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account account;

    @ManyToOne(targetEntity = Department.class, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
