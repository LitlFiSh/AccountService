package com.fishpound.accountservice.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orderapply")
public class OrderApply {
    @Id
    private Integer id;

    @Column(name = "apply_departemnt")
    private Integer applyDepartment;

    @Column(name = "apply_user")
    private Integer applyUser;

    @Column(name = "fund_code")
    private Integer fundCode;

    @Column(name = "apply_date")
    private Date applyDate;

    @Column(name = "total")
    private Double total;

    @Column(name = "dept_leader_sign")
    private boolean deptLeaderSign;

    @Column(name = "dept_leader_sign_date")
    private Date deptLeaderSignDate;

    @Column(name = "inst_leader_sign")
    private boolean instLeaderSign;

    @Column(name = "inst_leader_sign_date")
    private Date instLeaderSignDate;

    @Column(name = "status")
    private Integer status;

    @OneToMany(targetEntity = OrderList.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private List<OrderList> orderLists;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyDepartment() {
        return applyDepartment;
    }

    public void setApplyDepartment(Integer applyDepartment) {
        this.applyDepartment = applyDepartment;
    }

    public Integer getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Integer applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getFundCode() {
        return fundCode;
    }

    public void setFundCode(Integer fundCode) {
        this.fundCode = fundCode;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean isDeptLeaderSign() {
        return deptLeaderSign;
    }

    public void setDeptLeaderSign(boolean deptLeaderSign) {
        this.deptLeaderSign = deptLeaderSign;
    }

    public Date getDeptLeaderSignDate() {
        return deptLeaderSignDate;
    }

    public void setDeptLeaderSignDate(Date deptLeaderSignDate) {
        this.deptLeaderSignDate = deptLeaderSignDate;
    }

    public boolean isInstLeaderSign() {
        return instLeaderSign;
    }

    public void setInstLeaderSign(boolean instLeaderSign) {
        this.instLeaderSign = instLeaderSign;
    }

    public Date getInstLeaderSignDate() {
        return instLeaderSignDate;
    }

    public void setInstLeaderSignDate(Date instLeaderSignDate) {
        this.instLeaderSignDate = instLeaderSignDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderList> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(List<OrderList> orderLists) {
        this.orderLists = orderLists;
    }
}
