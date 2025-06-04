package me.kire.re.nourishment.domain.model;

import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
public record NourishmentTypeUnit(
        NourishmentTypeEnum nourishmentType,
        Integer unit
) implements NourishmentType {
}
