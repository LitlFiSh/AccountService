package com.fishpound.accountservice.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 与数据库对应的角色实体
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Set<Account> accountSet = new HashSet<>();

    @OneToMany(targetEntity = Menu.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rid", referencedColumnName = "id")
    private Set<Menu> menuSet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }
}
