package com.bling.dab.controller;

import com.bling.dab.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

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
        String originalFilename = file.getOriginalFilename();
        
        return null;
    }

    @RequestMapping(value = "/batchExport")
    public String batchExport() {
        batchService.batchExport();
        return "forward:/file";
    }
}