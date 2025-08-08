package com.project.management.documentType.service.impl;

import com.project.management.documentType.dto.DocumentTypeResponse;
import com.project.management.documentType.repository.DocumentTypeRepositoryCustom;
import com.project.management.documentType.service.DocumentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepositoryCustom documentTypeRepositoryCustom;

    public DocumentTypeServiceImpl(DocumentTypeRepositoryCustom documentTypeRepositoryCustom) {
        this.documentTypeRepositoryCustom = documentTypeRepositoryCustom;
    }

    @Override
    public List<DocumentTypeResponse> getAllDocumentType() {
        return documentTypeRepositoryCustom.getAllDocumentType();
    }
}
