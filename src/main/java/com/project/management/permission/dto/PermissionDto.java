package com.project.management.permission.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDto {
    /**
     * Identificador único del permiso.
     */
    @JsonProperty(value = "permission_id")
    private UUID permissionId;

    /**
     * Nombre del permiso.
     */
    @JsonProperty(value = "permission_name")
    private String permissionName;

    /**
     * Título del permiso.
     */
    @JsonProperty(value = "permission_title")
    private String permissionTitle;

    /**
     * Indica si el permiso está marcado (solo lectura).
     */
    @JsonProperty(value = "permission_checked", access = JsonProperty.Access.READ_ONLY)
    private Boolean permissionChecked;

    public PermissionDto(UUID permissionId, String permissionName, String permissionTitle, Boolean permissionChecked) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.permissionTitle = permissionTitle;
        this.permissionChecked = permissionChecked;
    }

    public static PermissionDto create(UUID permissionId, String permissionName, String permissionTitle, Boolean permissionChecked) {
        return new PermissionDto(
                permissionId,
                permissionName,
                permissionTitle,
                permissionChecked
        );
    }

    public UUID getPermissionId(){
        return permissionId;
    }
    /**
     * Obtiene el nombre del permiso.
     *
     * @return Nombre del permiso.
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Establece el nombre del permiso.
     *
     * @param permissionName Nombre del permiso.
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Obtiene el título del permiso.
     *
     * @return Título del permiso.
     */
    public String getPermissionTitle() {
        return permissionTitle;
    }

    /**
     * Establece el título del permiso.
     *
     * @param permissionTitle Título del permiso.
     */
    public void setPermissionTitle(String permissionTitle) {
        this.permissionTitle = permissionTitle;
    }

    /**
     * Obtiene el estado de marcado del permiso.
     *
     * @return Estado de marcado del permiso.
     */
    public Boolean getPermissionChecked() {
        return permissionChecked;
    }

    /**
     * Establece el estado de marcado del permiso.
     *
     * @param permissionChecked Estado de marcado del permiso.
     */
    public void setPermissionChecked(Boolean permissionChecked) {
        this.permissionChecked = permissionChecked;
    }
}
