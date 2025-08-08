package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DataUserOfMicrosoft {

    @JsonProperty(value = "@odata.context")
    private String context;
    private String[] businessPhones;
    private String displayName;
    private String givenName;
    private String jobTitle;
    private String mail;
    private String mobilePhone;
    private String officeLocation;
    private String preferredLanguage;
    private String surname;
    private String userPrincipalName;
    private String id;
}
