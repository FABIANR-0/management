package com.proyecto.demo.documentType.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DocumentTypeResponse {
    @JsonProperty(value = "document_type_id",required = true)
    private UUID documentTypeId;

    @JsonProperty(value = "code",required = true)
    private String code;

    public DocumentTypeResponse(UUID documentTypeId, String code) {
        this.documentTypeId = documentTypeId;
        this.code = code;
    }
}
