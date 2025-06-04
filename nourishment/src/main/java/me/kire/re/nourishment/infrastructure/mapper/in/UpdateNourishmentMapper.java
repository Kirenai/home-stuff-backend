package me.kire.re.nourishment.infrastructure.mapper.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.model.NourishmentType;
import me.kire.re.nourishment.domain.model.NourishmentTypePercentage;
import me.kire.re.nourishment.domain.model.NourishmentTypeUnit;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentTypePercentageRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentTypeRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentTypeUnitRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UpdateNourishmentMapper {

    // Map In Update to Domain
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "isAvailable", ignore = true)
    @Mapping(target = "nourishmentId", ignore = true)
    @Mapping(target = "nourishmentType", source = "type", qualifiedByName = "mapInNourishmentTypeUpdate")
    Nourishment mapInUpdateNourishmentRequestToNourishment(UpdateNourishmentRequest updateNourishmentRequest);

    @Named("mapInNourishmentTypeUpdate")
    default NourishmentType mapInNourishmentTypeUpdate(UpdateNourishmentTypeRequest updateNourishmentTypeRequest) {
        return switch (updateNourishmentTypeRequest) {
            case UpdateNourishmentTypePercentageRequest percentageRequest ->
                    this.mapInNourishmentTypePercentage(percentageRequest);
            case UpdateNourishmentTypeUnitRequest unitRequest -> this.mapInNourishmentTypeUnit(unitRequest);
        };
    }

    @Mapping(target = "percentage", source = "percentage", qualifiedByName = "mapInPercentage")
    NourishmentTypePercentage mapInNourishmentTypePercentage(UpdateNourishmentTypePercentageRequest updateNourishmentTypePercentageRequest);

    @Named("mapInPercentage")
    default Double mapInPercentage(Integer percentage) {
        return percentage / 100.0;
    }

    NourishmentTypeUnit mapInNourishmentTypeUnit(UpdateNourishmentTypeUnitRequest updateNourishmentTypeUnitRequest);
    //End Update
}
