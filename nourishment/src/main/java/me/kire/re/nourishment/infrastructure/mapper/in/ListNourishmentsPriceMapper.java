package me.kire.re.nourishment.infrastructure.mapper.in;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.infrastructure.rest.dto.price.ListNourishmentsPriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListNourishmentsPriceMapper {
    ListNourishmentsPriceResponse mapOutListNourishmentsPriceResponse(NourishmentPrice nourishmentPrice);
}
