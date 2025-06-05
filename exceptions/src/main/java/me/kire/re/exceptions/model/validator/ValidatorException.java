package me.kire.re.exceptions.model.validator;

import me.kire.re.exceptions.dto.validator.ErrorResponse;

import java.util.List;

public class ValidatorException extends RuntimeException {

    private final List<ErrorResponse> errorResponses;

    public ValidatorException(List<ErrorResponse> errorResponses) {
        super("Binding Error");
        this.errorResponses = errorResponses;
    }

    public List<ErrorResponse> createError() {
        return this.errorResponses;
    }

}
