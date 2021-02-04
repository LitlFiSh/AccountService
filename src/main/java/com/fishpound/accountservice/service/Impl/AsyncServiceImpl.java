package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.Notice;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.repository.DepartmentRepository;
import com.fishpound.accountservice.repository.NoticeRepository;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    NoticeRepository noticeRepository;

    /**
     * 异步发送通知到部门领导账号中
     * @param uid 申请人id
     * @param oid 申请单id
     * @param title 消息标题
     * @param content 消息内容
     */
    @Async
    @Override
    public void createNoticeToDeptLead(String uid, String oid, String title, String content) {
        Department department = userInfoRepository.getById(uid).getDepartment();
        List<UserInfo> list = userInfoRepository.findAllByAccount_Role_IdAndDepartment_DeptName(3, department.getDeptName());
        Notice notice = new Notice(title, content, oid);
        for(UserInfo userInfo : list){
            notice.setUserInfo(userInfo);
            noticeRepository.save(notice);
        }
    }

    /**
     * 发送消息到对应用户
     * @param uid 用户ID
     * @param title 消息标题
     * @param content 消息内容
     */
    @Async
    @Override
    public void createNoticeByUid(String uid, String oid, String title, String content) {
        UserInfo userInfo = userInfoRepository.getById(uid);
        Notice notice = new Notice(title, content, oid, userInfo);
        noticeRepository.save(notice);
    }
}
