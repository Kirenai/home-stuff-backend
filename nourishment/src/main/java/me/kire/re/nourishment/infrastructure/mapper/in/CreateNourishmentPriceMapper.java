package me.kire.re.nourishment.infrastructure.mapper.in;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.infrastructure.rest.dto.price.CreateNourishmentPriceRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.price.CreateNourishmentPriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateNourishmentPriceMapper {
    @Mapping(target = "nourishmentPriceId", ignore = true)
    NourishmentPrice mapInNourishmentPrice(String nourishmentId, CreateNourishmentPriceRequest createNourishmentPriceRequest);

    CreateNourishmentPriceResponse mapOutCreateNourishmentPriceResponse(NourishmentPrice nourishmentPrice);
}
