package com.proyecto.demo.email.service;

import com.proyecto.demo.user.entity.User;

public interface EmailSendService {
    void emailCodeVerification(String codeVerification);
    void resetPasswordUser(User user,String urlToken);
}
