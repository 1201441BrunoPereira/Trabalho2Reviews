package com.Review2_C.Review2_C.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailConfigImpl {


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public void sendSimpleMail(String email,String text, String subject)
    {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setText(text);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
            System.out.println("Email was sent");
        }

        catch (Exception e) {
            System.out.println("ERROR - Email not sent");
        }
    }



}
