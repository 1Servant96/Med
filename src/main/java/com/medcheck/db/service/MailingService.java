package com.medcheck.db.service;

import com.medcheck.db.entities.Mailing;
import com.medcheck.dto.request.MailingRequest;
import com.medcheck.dto.response.SimpleResponse;

public interface MailingService {
    void saveMailing(Mailing mailing);


    SimpleResponse sendMailing(MailingRequest mailingRequest);

    void sendEmail(String email, Mailing mailing);


    void sendEmail(String messageText, String email);
}
