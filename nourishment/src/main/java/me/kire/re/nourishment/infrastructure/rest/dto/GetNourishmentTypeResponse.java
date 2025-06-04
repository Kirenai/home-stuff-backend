package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "nourishmentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetNourishmentTypePercentageResponse.class, name = "PERCENTAGE"),
        @JsonSubTypes.Type(value = GetNourishmentTypeUnitResponse.class, name = "UNIT")
})
public sealed interface GetNourishmentTypeResponse
        permits GetNourishmentTypeUnitResponse, GetNourishmentTypePercentageResponse {
}
