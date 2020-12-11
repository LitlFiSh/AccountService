package com.fishpound.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 与数据库对应的部门实体
 */
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "dept_name")
    private String deptName;

    @JsonIgnore
    @OneToMany(targetEntity = UserInfo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Set<UserInfo> userInfoSet = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Set<UserInfo> getUserInfoSet() {
        return userInfoSet;
    }

    public void setUserInfoSet(Set<UserInfo> userInfoSet) {
        this.userInfoSet = userInfoSet;
    }
}
