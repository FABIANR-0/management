package com.proyecto.demo.documentType.service.impl;

import com.proyecto.demo.documentType.dto.DocumentTypeResponse;
import com.proyecto.demo.documentType.repository.DocumentTypeRepositoryCustom;
import com.proyecto.demo.documentType.service.DocumentTypeService;
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
