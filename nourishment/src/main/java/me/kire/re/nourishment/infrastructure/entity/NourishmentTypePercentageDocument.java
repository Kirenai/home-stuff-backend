package me.kire.re.nourishment.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class NourishmentTypePercentageDocument extends NourishmentTypeDocument {
    private Double percentage;
}
