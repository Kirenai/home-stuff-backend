package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateNourishmentRequest(
        @NotEmpty
        @Size(min = 2, max = 35)
        String name,
        @NotEmpty
        String imageUrl,
        String description,
        @Valid
        @NotNull
        UpdateNourishmentTypeRequest type
) {
}
