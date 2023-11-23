package com.shreyash.BankApplication.Service;

import com.shreyash.BankApplication.DTO.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailServiceInterface{

    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String mailFrom;


    @Override
    public void sendEmail(EmailDetails emaildetails) throws Exception {
        try{
            SimpleMailMessage simplemailmessage = new SimpleMailMessage();

            simplemailmessage.setFrom(mailFrom);
            simplemailmessage.setTo(emaildetails.getRecipient());
            simplemailmessage.setSubject(emaildetails.getSubject());
            simplemailmessage.setText(emaildetails.getEmailBody());

            mailSender.send(simplemailmessage);
        }
        catch(Exception e){
            throw new Exception("Email sending failed");
        }



    }

}
