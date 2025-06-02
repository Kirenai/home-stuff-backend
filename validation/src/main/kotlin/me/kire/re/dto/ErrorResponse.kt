package me.kire.re.dto

import org.springframework.validation.FieldError

data class ErrorResponse(
    val field: String,
    val defaultMessage: String,
) {
    constructor(fieldError: FieldError) : this(
        field = fieldError.field,
        defaultMessage = fieldError.defaultMessage ?: "No message provided"
    )
}