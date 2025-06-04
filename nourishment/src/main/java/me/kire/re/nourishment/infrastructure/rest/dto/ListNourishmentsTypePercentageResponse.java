package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = ListNourishmentsTypePercentageResponse.class)
public record ListNourishmentsTypePercentageResponse(
        NourishmentTypeEnum nourishmentType,
        Double percentage
) implements ListNourishmentsTypeResponse {

    @JsonCreator
    public ListNourishmentsTypePercentageResponse(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                                  @JsonProperty("percentage") Double percentage) {
        this.nourishmentType = NourishmentTypeEnum.PERCENTAGE;
        this.percentage = percentage;
    }

}
