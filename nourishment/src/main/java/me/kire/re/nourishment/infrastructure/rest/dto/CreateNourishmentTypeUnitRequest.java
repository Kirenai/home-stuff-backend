package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = CreateNourishmentTypeUnitRequest.class)
public record CreateNourishmentTypeUnitRequest(
        @NotNull
        NourishmentTypeEnum nourishmentType,
        @NotNull
        @Min(value = 0)
        Integer unit
) implements CreateNourishmentTypeRequest {

    @JsonCreator
    public CreateNourishmentTypeUnitRequest(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                            @JsonProperty("unit") Integer unit) {
        this.nourishmentType = NourishmentTypeEnum.UNIT;
        this.unit = unit;
    }

}
