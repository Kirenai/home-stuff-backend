package me.kire.re.nourishment.infrastructure.rest.dto.price;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateNourishmentPriceRequest(
        @NotNull
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("timestamp")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate timestamp
) {
}
