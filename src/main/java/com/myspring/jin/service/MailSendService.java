package com.myspring.jin.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {
    @Autowired   //context-mail에서 빈 등록했기 때문에 주입받을 수 있다. Spring에서 제공하는 MailSender.
    private JavaMailSenderImpl mailSender;

    private String getKey(int size) {
        return getAuthCode();
    }

    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    public String sendAuthMail(String mail)  throws MessagingException{
        String authKey = getKey(6);
        MimeMessage mailMessage = mailSender.createMimeMessage();
        String mailContent = "인증번호:   "+authKey ;     //보낼 메시지
        try {
            mailMessage.setSubject("내가보내는 이메일", "utf-8");
            mailMessage.setText(mailContent, "utf-8", "html");
            mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "fail to send mail";
        }
        return authKey;
    }
}
