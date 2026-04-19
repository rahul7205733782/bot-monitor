package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private static JavaMailSender mailSender;

    @Autowired
    public void setMailSender(JavaMailSender sender) {
        mailSender = sender;
    }

    public static void sendEmail(List<String> failedClients) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("your-email@gmail.com");
        message.setSubject("Chatbot Down Alert ⚠️");

        String body = "Bot Failure Alert 🚨\n\nFailed Clients:\n";
        body += String.join("\n", failedClients);

        message.setText(body);

        mailSender.send(message);

        System.out.println("Email Sent");
    }
}