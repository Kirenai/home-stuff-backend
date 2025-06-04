package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = ListNourishmentsTypeUnitResponse.class)
public record ListNourishmentsTypeUnitResponse(
        NourishmentTypeEnum nourishmentType,
        Integer unit
) implements ListNourishmentsTypeResponse {

    @JsonCreator
    public ListNourishmentsTypeUnitResponse(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                            @JsonProperty("unit") Integer unit) {
        this.nourishmentType = NourishmentTypeEnum.UNIT;
        this.unit = unit;
    }

}
