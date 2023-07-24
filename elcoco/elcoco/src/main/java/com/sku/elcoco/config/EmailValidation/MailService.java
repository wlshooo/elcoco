package com.sku.elcoco.config.EmailValidation;

import com.sku.elcoco.config.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail, String title, String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail,title,text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail = {}, "
            +"title = {}, text = {}",toEmail,title,text);
            e.getMessage();
        }
    }

    private SimpleMailMessage createEmailForm(String toMail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject(title);
        message.setText("인증번호 : " + text);

        return message;
    }
}
