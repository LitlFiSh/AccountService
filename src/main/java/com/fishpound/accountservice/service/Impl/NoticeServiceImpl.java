package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Notice;
import com.fishpound.accountservice.repository.NoticeRepository;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public void addNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticeRepository.deleteById(id);
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public int findNoticeUnread(String uid) {
        return noticeRepository.countByUserInfo_IdAndStateIsTrue(uid);
    }

    @Override
    public Notice getOne(Integer id) {
        Notice notice = noticeRepository.getByid(id);
        notice.setState(false);
        noticeRepository.save(notice);
        return notice;
    }

}
