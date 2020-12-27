package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Notice;
import com.fishpound.accountservice.repository.NoticeRepository;
import com.fishpound.accountservice.service.NoticeService;
import com.fishpound.accountservice.service.tools.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

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
        return noticeRepository.countByUidAndStateIsTrue(uid);
    }

    @Override
    public Notice getOne(Integer id) {
        return noticeRepository.getByid(id);
    }

    @Override
    public Page<Notice> findByUser(String uid, Integer page) {
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        return noticeRepository.findAllByUid(uid, pageTools.sortSingle());
    }
}
