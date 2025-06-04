package me.kire.re.nourishment.infrastructure.mapper.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.model.NourishmentType;
import me.kire.re.nourishment.domain.model.NourishmentTypePercentage;
import me.kire.re.nourishment.domain.model.NourishmentTypeUnit;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentTypePercentageRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentTypeRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentTypeUnitRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateNourishmentMapper {

    // Map In Create to Domain
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "isAvailable", ignore = true)
    @Mapping(target = "nourishmentId", ignore = true)
    @Mapping(target = "nourishmentType", source = "type", qualifiedByName = "mapInNourishmentTypeCreate")
    Nourishment mapInCreateNourishmentRequestToNourishment(CreateNourishmentRequest createNourishmentRequest);

    @Named("mapInNourishmentTypeCreate")
    default NourishmentType mapInNourishmentTypeCreate(CreateNourishmentTypeRequest createNourishmentTypeRequest) {
        return switch (createNourishmentTypeRequest) {
            case CreateNourishmentTypePercentageRequest percentageRequest ->
                    this.mapInNourishmentTypePercentage(percentageRequest);
            case CreateNourishmentTypeUnitRequest unitRequest -> this.mapInNourishmentTypeUnit(unitRequest);
        };
    }

    @Mapping(target = "percentage", source = "percentage", qualifiedByName = "mapInPercentage")
    NourishmentTypePercentage mapInNourishmentTypePercentage(CreateNourishmentTypePercentageRequest createNourishmentTypePercentageRequest);

    @Named("mapInPercentage")
    default Double mapInPercentage(Integer percentage) {
        return percentage / 100.0;
    }

    NourishmentTypeUnit mapInNourishmentTypeUnit(CreateNourishmentTypeUnitRequest createNourishmentTypeUnitRequest);
    //End Create
}
