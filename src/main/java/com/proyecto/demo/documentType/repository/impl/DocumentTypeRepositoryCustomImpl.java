package com.proyecto.demo.documentType.repository.impl;

import com.proyecto.demo.documentType.dto.DocumentTypeResponse;
import com.proyecto.demo.documentType.entity.DocumentType;
import com.proyecto.demo.documentType.entity.DocumentType_;
import com.proyecto.demo.documentType.repository.DocumentTypeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class DocumentTypeRepositoryCustomImpl implements DocumentTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DocumentTypeResponse> getAllDocumentType() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        List<DocumentTypeResponse> result = new ArrayList<>();

        try {
            CriteriaQuery<DocumentTypeResponse> cq = cb.createQuery(DocumentTypeResponse.class);

            Root<DocumentType> root = cq.from(DocumentType.class);

            cq.select(cb.construct(
                    DocumentTypeResponse.class,
                    root.get(DocumentType_.documentTypeId),
                    root.get(DocumentType_.code)
            ));

            TypedQuery<DocumentTypeResponse> query = manager.createQuery(cq);
            result = query.getResultList();
        }catch (Exception ex){
            log.error("error en la consulta criteria: getAllDocumentType [{}]",ex.getMessage());
        }
        manager.close();
        return result;
    }
}
