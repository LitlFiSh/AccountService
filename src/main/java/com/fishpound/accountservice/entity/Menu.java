package com.fishpound.accountservice.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @Column(name = "pid")
    private Integer pid;

    @ManyToOne(targetEntity = Role.class, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "rid", referencedColumnName = "id")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Menu.class, fetch = FetchType.EAGER, mappedBy = "pid")
    private Set<Menu> children = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
    }
}
