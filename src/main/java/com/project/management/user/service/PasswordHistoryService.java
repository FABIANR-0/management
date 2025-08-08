package com.project.management.user.service;

import com.project.management.user.entity.User;

public interface PasswordHistoryService {

    /**
     * Valida si la contraseña nueva es igual a la anterior
     *
     * @param user             usuario al que pertenerce dicho dato
     * @param requestPassword  Contraseña nueva a actualizar
     * @return                 Boleano segun la validacion
     */
    Boolean validateLastPassword(User user, String requestPassword);

    void addPasswordHistory(String passwordHistory,User user);
}
