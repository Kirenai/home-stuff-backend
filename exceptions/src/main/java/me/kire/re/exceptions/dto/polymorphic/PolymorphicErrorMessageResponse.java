package me.kire.re.exceptions.dto.polymorphic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PolymorphicErrorMessageResponse(
        Integer statusCode,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,
        String path,
        String message
) {
}
