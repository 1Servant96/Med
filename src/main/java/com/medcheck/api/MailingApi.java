package com.medcheck.api;

import com.medcheck.db.service.MailingService;
import com.medcheck.db.service.serviceimpl.EmailAlreadyExistException;
import com.medcheck.db.service.serviceimpl.SubscriptionService;
import com.medcheck.dto.request.MailingRequest;
import com.medcheck.dto.request.SubscriptionRequest;
import com.medcheck.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mailings")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Mailing API")
public class MailingApi {
    private final MailingService emailService;
    private final SubscriptionService subscriptionService;

    @Operation(summary = "Subscribe ", description = "Any user can subscribe for mailing and he will be saved in our database in subscription table, we need only email")
    @PostMapping("/subscribe")
    public SimpleResponse subscribe(@RequestBody SubscriptionRequest subscriptionRequest) throws EmailAlreadyExistException {
        return subscriptionService.save(subscriptionRequest);
    }

    @Operation(summary = "Send mailing", description = "This endpoint sends the mailing for all emails which saved in our database in subscriptions table ")
    @PostMapping("/message")
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse sendEmail(@RequestBody MailingRequest mailingRequest) {
        return emailService.sendMailing(mailingRequest);
    }
}
