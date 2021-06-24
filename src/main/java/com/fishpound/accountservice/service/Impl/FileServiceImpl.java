package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.File;
import com.fishpound.accountservice.repository.FileRepository;
import com.fishpound.accountservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 17:00
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public void addFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public void updateFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(Integer id) {
        fileRepository.deleteById(id);
    }

    @Override
    public File findAllByPurchaceIdAndDesc(Integer pid, String desc) {
        return fileRepository.getByPurchaceIdAndDescription(pid, desc);
    }

    @Override
    public List<File> findAllByPurchaceId(Integer pid) {
        return fileRepository.getAllByPurchaceId(pid);
    }

    @Override
    public List<File> findAll() {
        return fileRepository.findAll();
    }
}
