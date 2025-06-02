package me.kire.re.exception

import me.kire.re.dto.ErrorResponse

class ValidatorException : RuntimeException {
    private val errorResponse: List<ErrorResponse>

    constructor(errorResponse: List<ErrorResponse>) : super("Validation failed") {
        this.errorResponse = errorResponse
    }
}