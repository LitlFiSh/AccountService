package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Notice;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {
    void addNotice(Notice notice);
    void deleteNotice(Integer id);
    void updateNotice(Notice notice);
    int findNoticeUnread(String uid);
    Page<Notice> findAllByUser(String uid, Integer page);
    List<Notice> findAll(String uid);
    Notice getOne(Integer id);
}
