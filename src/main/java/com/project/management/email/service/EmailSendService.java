package com.project.management.email.service;

import com.project.management.user.entity.User;

public interface EmailSendService {
    void emailCodeVerification(String codeVerification);
    void resetPasswordUser(User user,String urlToken);
}
