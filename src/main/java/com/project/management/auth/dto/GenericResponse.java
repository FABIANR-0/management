package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GenericResponse<T> {

    @JsonProperty
    private T data;
    @JsonProperty
    private String message;
    @JsonProperty
    private HttpStatus httpStatus;

    public GenericResponse() {
        this(null, null, null);
    }

    public GenericResponse(String message, HttpStatus httpStatus) {
        this(null, message, httpStatus);
    }

    public GenericResponse(T data, String message, HttpStatus httpStatus) {
        this.data = data;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public T data() {
        return data;
    }

    public String message() {
        return message;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }
}
