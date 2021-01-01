package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    int countByUserInfo_IdAndStateIsTrue(String uid);
    Notice getByid(Integer id);
    Notice findAllByUserInfo_Id(String id);
}
