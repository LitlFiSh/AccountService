package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.File;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 17:03
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public JsonResult uploadFile(@RequestParam(value = "file")MultipartFile file,
                                 @RequestParam(value = "oid")String oid,
                                 @RequestParam(value = "status")String status)
    {
        try{
            byte[] data = file.getBytes();
            File f = new File();
            f.setFile(data);
            f.setDescription(status);
            f.setOid(oid);
            f.setCreateTime(new Date());
            f.setUpdateTime(new Date());
            fileService.addFile(f);
        } catch(Exception e){
            return ResultTool.fail(e.getMessage());
        }
        return ResultTool.success();
    }
}
