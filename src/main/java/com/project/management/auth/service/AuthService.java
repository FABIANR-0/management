package com.project.management.auth.service;

import com.project.management.auth.dto.MfaRequest;
import com.project.management.user.entity.User;
import com.project.management.auth.dto.LoginCustomerRequest;
import com.project.management.auth.dto.AuthCustomerResponse;

import java.util.UUID;

public interface AuthService {

    AuthCustomerResponse loginCustomer(LoginCustomerRequest request);
    AuthCustomerResponse mfaAuthenticationCustomer(MfaRequest request);
    String generateToken(String userName);
    void resendTwoFactorAuthentication(UUID userId);
    User getUserAuthenticated();
}
