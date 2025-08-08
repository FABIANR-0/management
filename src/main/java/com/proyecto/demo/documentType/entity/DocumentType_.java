package com.proyecto.demo.documentType.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DocumentType.class)
public abstract class DocumentType_ {
    public static volatile SingularAttribute<DocumentType, UUID> documentTypeId;
    public static volatile SingularAttribute<DocumentType,String> name;
    public static volatile SingularAttribute<DocumentType,String> code;
}
