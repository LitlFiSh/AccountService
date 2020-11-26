package com.fishpound.accountservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderlist")
public class OrderList {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "no")
    private String no;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "budget_unit_price")
    private Double budgetUnitPrice;

    @Column(name = "budget_total_price")
    private Double budgetTotalPrice;

    @Column(name = "reason")
    private String reason;

    @Column(name = "new_user")
    private String newUser;

    @ManyToOne(targetEntity = OrderApply.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private OrderApply orderApply;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getBudgetUnitPrice() {
        return budgetUnitPrice;
    }

    public void setBudgetUnitPrice(Double budgetUnitPrice) {
        this.budgetUnitPrice = budgetUnitPrice;
    }

    public Double getBudgetTotalPrice() {
        return budgetTotalPrice;
    }

    public void setBudgetTotalPrice(Double budgetTotalPrice) {
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

    public OrderApply getOrderApply() {
        return orderApply;
    }

    public void setOrderApply(OrderApply orderApply) {
        this.orderApply = orderApply;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
