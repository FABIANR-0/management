package com.proyecto.demo.common.criteria;

import com.proyecto.demo.common.object.StringValueObject;

/**
 * Clase que representa el valor de un filtro en los criterios de búsqueda.
 */
public final class FilterValue extends StringValueObject {
    /**
     * Constructor de FilterValue.
     *
     * @param value el valor del filtro
     */
    public FilterValue(String value) {
        super(value);
    }
}
