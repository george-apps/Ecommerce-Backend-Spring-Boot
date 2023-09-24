package com.appsgeorge.EcommerceBackend.service;

import com.appsgeorge.EcommerceBackend.exception.EmailFailException;
import com.appsgeorge.EcommerceBackend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.from}")
    private String fromAddress;

    @Value("${app.url}")
    private String url;

    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);

        return simpleMailMessage;
    }

    public void sendVerificationMailMessage(VerificationToken verificationToken) throws EmailFailException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Verification email to activate account");
        message.setText("Follow the link below to verify your account\n" +
                url + "/auth/verify?token=" + verificationToken.getToken());

        try{
            javaMailSender.send(message);
        }catch (MailException e){
            throw new EmailFailException();
        }


    }

}
