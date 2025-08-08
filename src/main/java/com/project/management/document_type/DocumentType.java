package com.project.management.document_type;

import lombok.Getter;

@Getter
public enum DocumentType {
    CC("Cédula de ciudadanía"),
    PP("Pasaporte"),
    CE("Cédula de extranjería"),
    RUC("RUC");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    // Método para validar el valor y devolver el tipo del enum
    public static DocumentType fromValue(String value) {
        return switch (value){
            case "Cédula de ciudadanía", "CEDULA" -> DocumentType.CC;
            case "Pasaporte","PASAPORTE" -> DocumentType.PP;
            case "Cédula de extranjería","CEDULA EXTRANJERIA" -> DocumentType.CE;
            case "RUC" -> DocumentType.RUC;
            default -> throw new IllegalArgumentException("Terminal network [" + value + "] not supported.");
        };
    }
}
