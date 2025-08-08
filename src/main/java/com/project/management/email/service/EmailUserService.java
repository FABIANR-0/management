package com.project.management.email.service;

import com.project.management.user.dto.UserAuthorizedDto;
import com.project.management.user.entity.User;

public interface EmailUserService {

    Boolean sendCodeVerification(User user);

    void recoveryPasswordEmailSent(User user, String urlToken);

    void changePassword(User user);

    void resetUserPassword(User user, String password);

    void sendUserCredentials(UserAuthorizedDto request, User userAuth, String randomPassword, String applicationName);

    Boolean testSendEmail(String mail, String smtp, String port);
}
