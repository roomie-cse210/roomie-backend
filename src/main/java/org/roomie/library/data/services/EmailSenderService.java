package org.roomie.library.data.services;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger( EmailSenderService.class);

    public String generateRandomPassword() {

        List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1));
    
        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword(8, rules);
        return password;
    }

    public String getBody(){
        String randomPassword = generateRandomPassword();
        return "Hey!! \n\nYour Roomie password has been reset to \"" + randomPassword + "\". Kindly use it to login. \n\nRegards \n\nTeam Roomie";
    }

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {

        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(getBody());
        message.setSubject(subject);
        mailSender.send(message);
        logger.info("Reset password mail sent to: " + toEmail);
        
    }

    
}


