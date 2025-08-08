package com.project.management.common.util;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuditEntity.class)
public abstract class AuditEntity_ {

    public static volatile SingularAttribute<AuditEntity, LocalDateTime> createdAt;
    public static volatile SingularAttribute<AuditEntity, LocalDateTime> updatedAt;
}
