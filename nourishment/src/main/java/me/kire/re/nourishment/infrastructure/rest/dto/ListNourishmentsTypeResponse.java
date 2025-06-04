package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "nourishmentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ListNourishmentsTypePercentageResponse.class, name = "PERCENTAGE"),
        @JsonSubTypes.Type(value = ListNourishmentsTypeUnitResponse.class, name = "UNIT")
})
public sealed interface ListNourishmentsTypeResponse
        permits ListNourishmentsTypeUnitResponse, ListNourishmentsTypePercentageResponse {
}
