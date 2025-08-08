package com.proyecto.demo.common.criteria;

import com.proyecto.demo.common.object.StringValueObject;

/**
 * Clase que representa el campo por el cual se realiza el ordenamiento en los criterios de búsqueda.
 */
public class OrderBy extends StringValueObject {
    /**
     * Constructor de OrderBy.
     *
     * @param value el valor del campo de ordenamiento
     */
    public OrderBy(String value) {
        super(value);
    }
}
