package com.fishpound.accountservice.result;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;

import java.util.Date;
import java.util.List;

/**
 * @author Litl_FiSh
 * @Date 2021/2/10 0:06
 */
public class ResultOrder {
    private String id;
    private String applyDepartment;
    private String applyUser;
    private String fundCode;
    private Date applyDate;
    private Double total;
    private Integer status;
    private List<OrderList> orderLists;
    private String uid;
    private String withdrawalReason;
    private boolean hasFile;
    private Date createTime;
    private Date updateTime;
    private Integer version;

    public ResultOrder() {
    }

    public ResultOrder(OrderApply orderApply) {
        this.id = orderApply.getId();
        this.applyDepartment = orderApply.getApplyDepartment();
        this.applyUser = orderApply.getApplyUser();
        this.fundCode = orderApply.getFundCode();
        this.applyDate = orderApply.getApplyDate();
        this.total = orderApply.getTotal();
        this.status = orderApply.getStatus();
        this.orderLists = orderApply.getOrderLists();
        this.uid = orderApply.getUid();
        this.withdrawalReason = orderApply.getWithdrawalReason();
        this.hasFile = orderApply.getFile() == null ? false : true;
        this.createTime = orderApply.getCreateTime();
        this.updateTime = orderApply.getUpdateTime();
        this.version = orderApply.getVersion();
    }

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

    public String getWithdrawalReason() {
        return withdrawalReason;
    }

    public void setWithdrawalReason(String withdrawalReason) {
        this.withdrawalReason = withdrawalReason;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
