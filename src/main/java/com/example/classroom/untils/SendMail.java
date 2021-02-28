package com.example.classroom.untils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendMail {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;



    public boolean sendLink(String mail,String link) {
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(mail);
            helper.setSubject("账号激活");
            StringBuffer sb = new StringBuffer();

            sb.append("<p>链接有效时间为30分钟</p>")
                    .append("<a href='https://www.cupidwall.cn/classroom/author/s_s/"+link+"'>点击激活账号</a>");
            helper.setText(sb.toString(), true);
        } catch (MessagingException e) {
            System.out.print("error");
            return false;
        }
        javaMailSender.send(message);
        return true;
    }


}
