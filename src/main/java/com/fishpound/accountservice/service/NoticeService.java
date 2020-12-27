package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Notice;
import org.springframework.data.domain.Page;

public interface NoticeService {
    void addNotice(Notice notice);
    void deleteNotice(Integer id);
    void updateNotice(Notice notice);
    int findNoticeUnread(String uid);
    Notice getOne(Integer id);
    Page<Notice> findByUser(String uid, Integer page);
}
