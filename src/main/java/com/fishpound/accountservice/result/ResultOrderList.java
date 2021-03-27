package com.fishpound.accountservice.result;

import com.fishpound.accountservice.entity.OrderList;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author Litl_FiSh
 * @Date 2021/3/27 10:34
 */
public class ResultOrderList {
    private String id;
    private String name;
    private String type;
    private String configuration;
    private String unit;
    private Integer quantity;
    private Double budgetUnitPrice;
    private Double budgetTotalPrice;
    private String reason;
    private String newUser;
    private Integer status;
    private String opinion;
    private String departmentName;
    private String applyUsername;

    public ResultOrderList(OrderList orderList, String username, String deptName) {
        this.id = orderList.getId();
        this.name = orderList.getName();
        this.type = orderList.getType();
        this.configuration = orderList.getConfiguration();
        this.unit = orderList.getUnit();
        this.quantity = orderList.getQuantity();
        this.budgetUnitPrice = orderList.getBudgetUnitPrice();
        this.budgetTotalPrice = orderList.getBudgetTotalPrice();
        this.reason = orderList.getReason();
        this.newUser = orderList.getNewUser();
        this.status = orderList.getStatus();
        this.opinion = orderList.getOpinion();
        this.applyUsername = username;
        this.departmentName = deptName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getApplyUsername() {
        return applyUsername;
    }

    public void setApplyUsername(String applyUsername) {
        this.applyUsername = applyUsername;
    }
}
