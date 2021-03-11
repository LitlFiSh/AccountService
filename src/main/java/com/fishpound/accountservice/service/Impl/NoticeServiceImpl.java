package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Notice;
import com.fishpound.accountservice.repository.NoticeRepository;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.NoticeService;
import com.fishpound.accountservice.service.tools.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<Notice> findAllByUser(String uid, Integer page) {
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        return noticeRepository.findAllByUserInfo_Id(uid, pageTools.sortSingle());
    }

    @Override
    public List<Notice> findAll(String uid) {
        return noticeRepository.findAllByUserInfo_Id(uid);
    }

    @Override
    public Notice getOne(Integer id) {
        Notice notice = noticeRepository.getByid(id);
        notice.setState(false);
        noticeRepository.save(notice);
        return notice;
    }

}
