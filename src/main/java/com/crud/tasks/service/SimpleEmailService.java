package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEmailService {

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private final JavaMailSender javaMailSender;


    public void send(final Mail mail,TrelloAction actionChooser) {
        log.info("Starting email preparation...");
//       //Previous
//        try {
//            SimpleMailMessage mailMessage = createMailMessage(mail);
//            javaMailSender.send(mailMessage);

        try {
            javaMailSender.send(createMimeMessage(mail, actionChooser));
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

     MimeMessagePreparator createMimeMessage(final Mail mail, TrelloAction actionChooser) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            if (actionChooser.equals(TrelloAction.CREATE_CARD)) {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
            } else {
                messageHelper.setText(mailCreatorService.buildTrelloTasksEmail(mail.getMessage()), true);
            }
        };
    }



/*  //Previous
private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        return mailMessage;
    }*/

/*  //Previous
    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCc()).ifPresent(mailMessage::setCc);
        return mailMessage;
    }*/
}