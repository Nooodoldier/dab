package com.bling.dab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Administrator on 2019/2/23 0023.
 */
@ApiIgnore
@Controller
public class PicController {


    @RequestMapping(value = "/pic")
    public  String  pic(){
        return "pic.html";
    }
    @RequestMapping(value = "/upPic")
    public  String  upPic(){
        return "";
    }

    @RequestMapping(value = "/downPic")
    public  String  downPic(){
        return "";
    }
}
