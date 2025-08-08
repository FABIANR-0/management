package com.project.management.auth.dto;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * Respuesta de la validación reCAPTCHA.
 */
@Getter
public class RecaptchaResponse {

    private Boolean success;
    private Double score;
    private String action;
    private Timestamp challenge_ts;
    private String hostname;

}
