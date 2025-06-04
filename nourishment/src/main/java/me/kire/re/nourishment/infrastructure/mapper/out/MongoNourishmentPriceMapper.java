package me.kire.re.nourishment.infrastructure.mapper.out;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.infrastructure.entity.NourishmentPriceDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDate;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {LocalDate.class}
)
public interface MongoNourishmentPriceMapper {
    @Mapping(target = "timestamp", expression = "java(nourishmentPrice.timestamp() != null ? nourishmentPrice.timestamp() : LocalDate.now())")
    NourishmentPriceDocument mapInNourishmentPrice(NourishmentPrice nourishmentPrice);

    NourishmentPrice mapOutNourishmentPrice(NourishmentPriceDocument nourishmentPriceDocument);
}
