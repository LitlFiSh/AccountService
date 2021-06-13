package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Litl_FiSh
 * @Date 2021/6/12 17:03
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;
}
