package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = GetNourishmentTypePercentageResponse.class)
public record GetNourishmentTypePercentageResponse(
        NourishmentTypeEnum nourishmentType,
        Double percentage
) implements GetNourishmentTypeResponse {

    @JsonCreator
    public GetNourishmentTypePercentageResponse(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                                @JsonProperty("percentage") Double percentage) {
        this.nourishmentType = NourishmentTypeEnum.PERCENTAGE;
        this.percentage = percentage;
    }

}
