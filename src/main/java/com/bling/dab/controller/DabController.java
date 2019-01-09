package com.bling.dab.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hxp
 * @date: 2019/1/9 15:35
 * @description:
 */
@RestController
@EnableAutoConfiguration
public class DabController {
    @RequestMapping("/dab")
    private String dab(){
        return "dab!";
    }
}
