package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Notice;
import org.springframework.data.domain.Page;

public interface NoticeService {
    void addNotice(Notice notice);
    void deleteNotice(Integer id);
    void updateNotice(Notice notice);
    int findNoticeUnread(String uid);
    Page<Notice> findAllByUser(String uid, Integer page);
    Notice getOne(Integer id);
}
