package me.kire.re.nourishment.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nourishment_prices")
public class NourishmentPriceDocument {
    @Id
    private String nourishmentPriceId;
    private BigDecimal price;
    private LocalDate timestamp;
    private String nourishmentId;
}
