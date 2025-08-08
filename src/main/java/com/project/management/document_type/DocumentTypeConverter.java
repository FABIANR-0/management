package com.project.management.document_type;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {

    @Override
    public String convertToDatabaseColumn(DocumentType documentType) {
        return documentType.getValue();
    }

    @Override
    public DocumentType convertToEntityAttribute(String s) {
        return DocumentType.fromValue(s);
    }
}
