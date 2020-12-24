package com.fishpound.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orderapply")
public class OrderApply {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "apply_department")
    private String applyDepartment;

    @Column(name = "apply_user")
    private String applyUser;

    @Column(name = "fund_code")
    private String fundCode;

    @Column(name = "apply_date")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyDate;

    @Column(name = "total")
    private Double total;

    @JsonIgnore
    @Column(name = "dept_leader_sign")
    private boolean deptLeaderSign;

    @JsonIgnore
    @Column(name = "dept_leader_sign_date")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date deptLeaderSignDate;

    @JsonIgnore
    @Column(name = "inst_leader_sign")
    private boolean instLeaderSign;

    @JsonIgnore
    @Column(name = "inst_leader_sign_date")
    private Date instLeaderSignDate;

    @Column(name = "status")
    private Integer status;

    @OneToMany(targetEntity = OrderList.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    @JsonManagedReference
    private List<OrderList> orderLists;

    @JsonIgnore
    @Column(name = "uid")
    private String uid;

    @JsonIgnore
    @Column(name = "file")
    private byte[] file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyDepartment() {
        return applyDepartment;
    }

    public void setApplyDepartment(String applyDepartment) {
        this.applyDepartment = applyDepartment;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
