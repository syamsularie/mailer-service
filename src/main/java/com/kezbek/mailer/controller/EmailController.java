package com.kezbek.mailer.controller;

import com.kezbek.mailer.dto.EmailDetails;
import com.kezbek.mailer.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendMessage(@RequestBody EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailDetails.getFromEmail());
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
        emailService.sendMessage(simpleMailMessage);

        return "Email sent successfully";
    }

}
