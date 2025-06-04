package me.kire.re.nourishment.domain.model;

import lombok.Builder;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;

@Builder
public record NourishmentTypePercentage(
        NourishmentTypeEnum nourishmentType,
        Double percentage
) implements NourishmentType {
}
