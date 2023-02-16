package org.roomie.library.data.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger( EmailSenderService.class);

    public String getBody(String newpassword){
        return "Hey!! \n\nYour Roomie password has been reset to \"" + newpassword + "\". Kindly use it to login. \n\nRegards \n\nTeam Roomie";
    }

    public void sendSimpleEmail(String toEmail,
                                String newpassword) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(getBody(newpassword));
        message.setSubject("Password reset for Roomie");
        mailSender.send(message);
        logger.info("Reset password mail sent to: " + toEmail);
    }

    
}


