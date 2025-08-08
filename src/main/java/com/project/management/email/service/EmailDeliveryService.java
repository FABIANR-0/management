package com.project.management.email.service;

import com.project.management.email.dto.EmailRequest;

public interface EmailDeliveryService {

    Boolean send(EmailRequest request);
    Boolean sendTest(EmailRequest request);
}
