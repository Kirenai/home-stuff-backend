package me.kire.re.nourishment.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "nourishmentType")
@JsonSubTypes({
        @Type(value = CreateNourishmentTypePercentageRequest.class, name = "PERCENTAGE"),
        @Type(value = CreateNourishmentTypeUnitRequest.class, name = "UNIT")
})
public sealed interface CreateNourishmentTypeRequest
        permits CreateNourishmentTypePercentageRequest, CreateNourishmentTypeUnitRequest {
}
