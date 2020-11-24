package com.fishpound.accountservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderlist")
public class OrderList {
    @Id
    private Integer id;

    @Column(name = "no")
    private Integer no;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "budget_unit_price")
    private Integer budgetUnitPrice;

    @Column(name = "budget_total_price")
    private Integer budgetTotalPrice;

    @Column(name = "reason")
    private String reason;

    @Column(name = "new_user")
    private String newUser;

    @Column(name = "old")
    private String old;

    @ManyToOne(targetEntity = OrderApply.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private OrderApply orderApply;

    @Column(name = "status")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBudgetUnitPrice() {
        return budgetUnitPrice;
    }

    public void setBudgetUnitPrice(Integer budgetUnitPrice) {
        this.budgetUnitPrice = budgetUnitPrice;
    }

    public Integer getBudgetTotalPrice() {
        return budgetTotalPrice;
    }

    public void setBudgetTotalPrice(Integer budgetTotalPrice) {
        this.budgetTotalPrice = budgetTotalPrice;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public OrderApply getOrderApply() {
        return orderApply;
    }

    public void setOrderApply(OrderApply orderApply) {
        this.orderApply = orderApply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
