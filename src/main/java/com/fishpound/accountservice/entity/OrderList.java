package com.fishpound.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orderlist")
public class OrderList {
    @Id
    @Column(name = "id")
    private String id;

    /*@Column(name = "no")
    private String no;*/

    @NotNull(message = "物资名称不能为空")
    @Column(name = "name")
    private String name;

    @NotNull(message = "品牌型号不能为空")
    @Column(name = "type")
    private String type;

    @NotNull(message = "配置或技术参数不能为空")
    @Column(name = "configuration")
    private String configuration;

    @NotNull(message = "单位不能为空")
    @Column(name = "unit")
    private String unit;

    @NotNull(message = "数量不能为空")
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull(message = "预算单价不能为空")
    @Column(name = "budget_unit_price")
    private Double budgetUnitPrice;

    @NotNull(message = "预算总价不能为空")
    @Column(name = "budget_total_price")
    private Double budgetTotalPrice;

    @NotNull(message = "申请原因不能为空")
    @Column(name = "reason")
    private String reason;

    @NotNull(message = "新设备使用人不能为空")
    @Column(name = "new_user")
    private String newUser;

    @ManyToOne(targetEntity = OrderApply.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    @JsonBackReference
    private OrderApply orderApply;

//    @Column(name = "status")
//    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getNo() {
//        return no;
//    }
//
//    public void setNo(String no) {
//        this.no = no;
//    }

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
}
