package com.medcheck.db.service.serviceimpl;

import com.medcheck.db.entities.Mailing;
import com.medcheck.db.entities.Subscription;
import com.medcheck.db.repository.MailingRepository;
import com.medcheck.db.repository.SubscriptionRepository;
import com.medcheck.db.service.MailingService;
import com.medcheck.dto.request.MailingRequest;
import com.medcheck.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailingServiceImpl implements MailingService {

    private final SubscriptionRepository subscriptionRepository;
    private final JavaMailSender emailSender;
    private final MailingRepository mailingRepository;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void saveMailing(Mailing mailing) {
        log.info("successfully works the save mailing method");
        mailingRepository.save(mailing);
    }

    @Override
    public SimpleResponse sendMailing(MailingRequest mailingRequest) {
        Mailing mailing = new Mailing();
        BeanUtils.copyProperties(mailingRequest, mailing);
        List<Subscription> subscribers = subscriptionRepository.findAll();
        for (Subscription subscriber : subscribers) {
            sendEmail(subscriber.getEmail(), mailing);
        }
        saveMailing(mailing);
        log.info("successfully works the send mailing method");
        return new SimpleResponse("Successfully sent", "ok");
    }

    @Override
    public void sendEmail(String email, Mailing mailing) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject(mailing.getMailingName());
        message.setText(mailing.getDescription() + "\n" + mailing.getImage()
                + "\n" + "Дата начала акции: " + mailing.getMailingDateOfStart() + "\n" + "Дата окончании акции: " + mailing.getMailingDateOfEnd());
        message.setBcc();
        emailSender.send(message);
        log.info("successfully works the send email method(mailing)");
    }



    @Override
    public void sendEmail(String messageText, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setText(messageText);
        message.setBcc();
        emailSender.send(message);
        log.info("successfully works the send email to user subscribe");
    }

}
