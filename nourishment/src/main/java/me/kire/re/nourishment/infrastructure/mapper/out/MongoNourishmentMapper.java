package me.kire.re.nourishment.infrastructure.mapper.out;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.model.NourishmentType;
import me.kire.re.nourishment.domain.model.NourishmentTypePercentage;
import me.kire.re.nourishment.domain.model.NourishmentTypeUnit;
import me.kire.re.nourishment.infrastructure.entity.NourishmentDocument;
import me.kire.re.nourishment.infrastructure.entity.NourishmentTypeDocument;
import me.kire.re.nourishment.infrastructure.entity.NourishmentTypePercentageDocument;
import me.kire.re.nourishment.infrastructure.entity.NourishmentTypeUnitDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import static me.kire.re.nourishment.domain.enums.NourishmentTypeEnum.PERCENTAGE;
import static me.kire.re.nourishment.domain.enums.NourishmentTypeEnum.UNIT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MongoNourishmentMapper {
    // Map In to Document
    NourishmentDocument mapInNourishmentToNourishmentEntity(Nourishment nourishment);

    default NourishmentTypeDocument mapInNourishmentTypeDocument(NourishmentType nourishmentType) {
        return switch (nourishmentType) {
            case NourishmentTypeUnit unit -> this.mapInNourishmentTypeUnitDocument(unit);
            case NourishmentTypePercentage percentage -> this.mapInNourishmentTypePercentageDocument(percentage);
        };
    }

    NourishmentTypeUnitDocument mapInNourishmentTypeUnitDocument(NourishmentTypeUnit nourishmentTypeUnit);

    NourishmentTypePercentageDocument mapInNourishmentTypePercentageDocument(NourishmentTypePercentage nourishmentTypePercentage);
    // End Document


    // Map Out to Domain
    Nourishment mapOutNourishmentEntityToNourishment(NourishmentDocument nourishmentDocument);

    default NourishmentType mapOutNourishmentTypeDocument(NourishmentTypeDocument nourishmentTypeDocument) {
        if (UNIT.equals(nourishmentTypeDocument.getNourishmentType())) {
            return this.mapOutNourishmentTypeUnit(((NourishmentTypeUnitDocument) nourishmentTypeDocument));
        }
        if (PERCENTAGE.equals(nourishmentTypeDocument.getNourishmentType())) {
            return this.mapOutNourishmentTypePercentage(((NourishmentTypePercentageDocument) nourishmentTypeDocument));
        }
        return null;
    }

    NourishmentTypeUnit mapOutNourishmentTypeUnit(NourishmentTypeUnitDocument nourishmentTypeUnitDocument);

    NourishmentTypePercentage mapOutNourishmentTypePercentage(NourishmentTypePercentageDocument nourishmentTypePercentageDocument);
    // End Domain
}
