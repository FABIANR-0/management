package com.proyecto.demo.common.criteria;

import com.proyecto.demo.common.object.StringValueObject;

/**
 * Clase que representa el campo de un filtro utilizado en los criterios de búsqueda.
 */
public final class FilterField extends StringValueObject {
    /**
     * Constructor de la clase FilterField.
     *
     * @param value el valor del campo
     */
    public FilterField(String value) {
        super(value);
    }
}
