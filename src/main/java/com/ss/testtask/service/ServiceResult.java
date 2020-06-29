package com.ss.testtask.service;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResult<T> {
    private final T result;
    private final String error;

    public ServiceResult() {
        this.result = null;
        this.error = null;
    }

    public ServiceResult(T result) {
        this.result = result;
        this.error = null;
    }

    public ServiceResult(String error) {
        this.result = null;
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public boolean hasError() {
        return Objects.nonNull(error);
    }
}
