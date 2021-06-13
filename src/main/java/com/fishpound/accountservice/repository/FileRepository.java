package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 16:54
 */
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> getAllByOid(String oid);
    File getByOidAndDescription(String oid, String description);
}
