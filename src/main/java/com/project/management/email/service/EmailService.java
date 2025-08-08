package com.project.management.email.service;

import com.project.management.email.entity.Email;

import java.util.List;

public interface EmailService {

    void save(Email email);

    List<Email> getPending();
}
