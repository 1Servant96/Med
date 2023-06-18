package com.medcheck.db.service.serviceimpl;

import com.medcheck.db.entities.Subscription;
import com.medcheck.db.repository.SubscriptionRepository;
import com.medcheck.db.service.MailingService;
import com.medcheck.dto.request.SubscriptionRequest;
import com.medcheck.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String sender;
    private final MailingService mailingService;

    @Override
    public SimpleResponse save(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = new Subscription();
        subscription.setEmail(subscriptionRequest.getEmail());
        if (subscriptionRepository.existsByEmail(subscription.getEmail())) {
            log.error("Email already registered");
            throw new EmailAlreadyExistException("Email already registered");
        }
        mailingService.sendEmail("Hello, you are successfully subscribed to our mailings!", subscriptionRequest.getEmail());
        subscriptionRepository.save(subscription);
        log.info("successfully works the save method");
        sendEmail(subscription);
        return new SimpleResponse("Successfully subscribed", "ok");
    }

    @Override
    public List<Subscription> findAll() {
        log.info("successfully works the find all subscriptions method");
        return subscriptionRepository.findAll();
    }

    @Override
    public void sendEmail(Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(subscription.getEmail());
        message.setSubject("SUBSCRIBED");
        message.setText("Hello! You have subscribed on gadgetarium's mailing.");
        message.setBcc();
        emailSender.send(message);
        log.info("successfully works the send email method(subscription)");
    }}
