package com.project.management.email.service;

import com.project.management.email.dto.EmailRequest;

public interface EmailService {
    void sendEmail(EmailRequest request);
}
