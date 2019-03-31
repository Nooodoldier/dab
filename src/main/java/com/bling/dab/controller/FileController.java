package com.bling.dab.controller;

import com.bling.dab.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: hxp
 * @date: 2019/2/21 19:52
 * @description:
 */
@Controller
public class FileController {


    @Autowired
    private BatchService batchService;

    @RequestMapping(value = "/file")
    public String file(){
        return "file.html";
    }
    
    @RequestMapping(value = "/batchImport")
    public String batchImport(MultipartFile file) {
        batchService.batchImport(file);
        return "forward:/file";
    }

    @RequestMapping(value = "/batchExport")
    public void batchExport(HttpServletResponse response) {
        batchService.batchExport(response);
    }
}
