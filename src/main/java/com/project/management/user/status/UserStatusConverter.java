package com.project.management.user.status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor de atributos para el estado de usuario.
 */
@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    /**
     * Convierte el estado de usuario en su valor correspondiente para ser almacenado en la base de datos.
     *
     * @param status el estado de usuario a convertir
     * @return el valor correspondiente del estado de usuario
     */
    @Override
    public String convertToDatabaseColumn(UserStatus status) {
        return status.getValue();
    }
    /**
     * Convierte el valor almacenado en la base de datos en el estado de usuario correspondiente.
     *
     * @param value el valor almacenado en la base de datos
     * @return el estado de usuario correspondiente al valor
     */
    @Override
    public UserStatus convertToEntityAttribute(String value) {
        return UserStatus.fromValue(value);
    }
}
