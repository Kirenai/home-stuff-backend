package me.kire.re.nourishment.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kire.re.nourishment.domain.enums.NourishmentTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nourishmentType")
public class NourishmentTypeDocument {
    private NourishmentTypeEnum nourishmentType;
}
