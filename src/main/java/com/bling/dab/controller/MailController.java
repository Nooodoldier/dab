package com.bling.dab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.internet.MimeMessage;

/**
 * @author: hxp
 * @date: 2019/4/2 15:04
 * @description:
 */
@ApiIgnore
@RestController
public class MailController {

    private static String content = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<title>测试邮件2</title>"
            + "<meta name=\"content-type\" content=\"text/html; charset=UTF-8\">"
            + "</head>"
            + "<body>"
            + "<p style=\"color:#0FF\">这是一封测试邮件~</p>"
            + "</body>"
            + "</html>";

    /**
     * 自动注入
     */
    @Autowired
    private JavaMailSender mailSender;
    @RequestMapping(value = "/sendEmail/{name}", method = RequestMethod.GET)
    public String sendEmail(@PathVariable("name") String name) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            //设置发信人，发信人需要和spring.mail.username配置的一样否则报错
            message.setFrom("15810665479@163.com");
            //补全地址
            name+="@qq.com";
            //设置收信人
            message.setTo(name);
            //设置主题
            message.setSubject("测试邮件主题");
            //第二个参数true表示使用HTML语言来编写邮件
            message.setText(content,true);
            this.mailSender.send(mimeMessage);

            return "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}
