package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    int countByUidAndStateIsTrue(String uid);
    Notice getByid(Integer id);
    Page<Notice> findAllByUid(String uid, Pageable pageable);
}
