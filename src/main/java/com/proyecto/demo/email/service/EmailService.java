package com.proyecto.demo.email.service;

import com.proyecto.demo.email.dto.EmailRequest;

public interface EmailService {
    void sendEmail(EmailRequest request);
}
