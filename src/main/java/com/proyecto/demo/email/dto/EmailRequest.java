package com.proyecto.demo.email.dto;

import lombok.Getter;

@Getter
public class EmailRequest {

    private String destination;
    private String[] copy;
    private String subject;
    private String body;
    private String attachedName;
    private String attached;
    private boolean queue;

    public EmailRequest(String destination, String subject, String body, String attachedName, String attached) {
        this.destination = destination;
        this.subject = subject;
        this.body = body;
        this.attachedName = attachedName;
        this.attached = attached;
    }

    public static EmailRequest create(String destination, String subject, String body, String attachedName, String attached){
        return new EmailRequest(
                destination,
                subject,
                body,
                attachedName,
                attached
        );
    }
}
