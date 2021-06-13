package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.File;

import java.util.List;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 16:57
 */
public interface FileService {
    void addFile(File file);
    void updateFile(File file);
    void deleteFile(Integer id);
    File findAllByOidAndDesc(String oid, String desc);
    List<File> findAllByOid(String oid);
    List<File> findAll();
}
