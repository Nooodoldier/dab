package com.bling.dab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DabController.class);

    @RequestMapping("/dab")
    private String dab(){
        System.out.println("dab01");
        logger.info("dab01===========logger");
        return "dab!";
    }


}
