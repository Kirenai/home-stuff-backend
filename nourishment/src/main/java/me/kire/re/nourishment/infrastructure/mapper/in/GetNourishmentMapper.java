package me.kire.re.nourishment.infrastructure.mapper.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.model.NourishmentType;
import me.kire.re.nourishment.domain.model.NourishmentTypePercentage;
import me.kire.re.nourishment.domain.model.NourishmentTypeUnit;
import me.kire.re.nourishment.infrastructure.rest.dto.GetNourishmentResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.GetNourishmentTypePercentageResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.GetNourishmentTypeResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.GetNourishmentTypeUnitResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GetNourishmentMapper {

    // Map Out to GetResponse
    @Mapping(target = "type", source = "nourishmentType", qualifiedByName = "mapOutGetNourishmentTypeResponse")
    GetNourishmentResponse mapOutNourishmentToGetNourishmentResponse(Nourishment nourishment);

    @Named("mapOutGetNourishmentTypeResponse")
    default GetNourishmentTypeResponse mapOutGetNourishmentTypeResponse(NourishmentType nourishmentType) {
        return switch (nourishmentType) {
            case NourishmentTypePercentage percentage -> this.mapOutGetNourishmentTypePercentageResponse(percentage);
            case NourishmentTypeUnit unit -> this.mapOutGetNourishmentTypeUnitResponse(unit);
        };
    }

    GetNourishmentTypePercentageResponse mapOutGetNourishmentTypePercentageResponse(NourishmentTypePercentage nourishmentTypePercentage);

    GetNourishmentTypeUnitResponse mapOutGetNourishmentTypeUnitResponse(NourishmentTypeUnit nourishmentTypeUnit);
    // End GetResponse
}
