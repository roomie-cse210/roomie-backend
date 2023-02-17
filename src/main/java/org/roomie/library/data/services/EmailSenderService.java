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

    public String getResetPasswordBody(String newpassword){
        return "Hey!! \n\nYour Roomie password has been reset to \"" + newpassword + "\". Kindly use it to login. \n\nRegards, \nTeam Roomie";
    }
    public String getEmailOTPBody(String OTP){
        return "Hey!! \n\n OTP to validate your roomie registration is \"" + OTP + "\". Kindly use it to validate your registration. \n\nRegards, \nTeam Roomie";
    }

    public void sendResetPasswordEmail(String toEmail,
                                String newpassword) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(getResetPasswordBody(newpassword));
        message.setSubject("Password reset for Roomie");
        mailSender.send(message);
        logger.info("Reset password email sent to: " + toEmail);
    }

    public void sendOTPEmail(String toEmail,
                                String OTP) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(getEmailOTPBody(OTP));
        message.setSubject("OTP for Roomie Registration");
        mailSender.send(message);
        logger.info("OTP email sent to: " + toEmail);
    }

    
}


