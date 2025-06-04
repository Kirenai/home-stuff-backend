package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = GetNourishmentTypeUnitResponse.class)
public record GetNourishmentTypeUnitResponse(
        NourishmentTypeEnum nourishmentType,
        Integer unit
) implements GetNourishmentTypeResponse {

    @JsonCreator
    public GetNourishmentTypeUnitResponse(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                          @JsonProperty("unit") Integer unit) {
        this.nourishmentType = NourishmentTypeEnum.UNIT;
        this.unit = unit;
    }

}
