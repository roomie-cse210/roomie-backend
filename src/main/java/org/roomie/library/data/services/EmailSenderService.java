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

    public String getEmailSignupOTPBody(String OTP){
        return "Hey!! \n\n Code to validate your roomie registration is \"" + OTP + "\". Kindly use it to validate your registration request. \n\nRegards, \nTeam Roomie";
    }
    public String getEmailForgotPasswordOTPBody(String OTP){
        return "Hey!! \n\n Code to validate your roomie password reset request is \"" + OTP + "\". Kindly use it to validate your password reset request. \n\nRegards, \nTeam Roomie";
    }

    public String getInviteEmailBody(String requesterEmail, String requesterName){
        return "Hey!! \n\n You have received a new roommate request from " + requesterName + ". We think you guys will be perfect roommates. You can reach out to " + requesterName + " at: " +  requesterEmail + ". \n\nRegards, \nTeam Roomie";
    }

    public String getNewMatchingRoomieEmailBody(String matchingRoomieName){
        return "Hey!! \n\n We have found a new matching roommie for you. The Roomie name is: " + matchingRoomieName + ". We think you guys will be perfect roommates. To explore further, you can login to roomie.com and connect with the new roomie :). \n\nRegards, \nTeam Roomie";
    }

    public void sendSignupOTPEmail(String toEmail,
                                String OTP) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(getEmailSignupOTPBody(OTP));
        message.setSubject("Code for Roomie Registration");
        mailSender.send(message);
        logger.info("Code email sent to: " + toEmail);
    }
    public void sendForgotPasswordOTPEmail(String toEmail,
                                String OTP) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(getEmailForgotPasswordOTPBody(OTP));
        message.setSubject("Code for Roomie Password Reset");
        mailSender.send(message);
        logger.info("Code email sent to: " + toEmail);
    }

    public void sendEmailInvite(String requesterEmail,
                                String receiverEmail, String requesterName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setText(getInviteEmailBody(requesterEmail, requesterName));
        message.setSubject("New Roomie Request Received");
        mailSender.send(message);
        logger.info("New Roomie Request sent to: " + receiverEmail);
    }

    public void sendEmailForNewMatchingRoomie(String receiverEmail, String matchingRoomieName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setText(getNewMatchingRoomieEmailBody(matchingRoomieName));
        message.setSubject("New potential Roomie available");
        mailSender.send(message);
        logger.info("New potential Roomie available: " + receiverEmail);
    }
    
}


