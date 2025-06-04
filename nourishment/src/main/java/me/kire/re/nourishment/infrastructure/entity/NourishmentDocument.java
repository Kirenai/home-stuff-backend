package me.kire.re.nourishment.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Nourishment
 *
 * @author Kirenai RE
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "nourishments")
public class NourishmentDocument {

    @Id
    private String nourishmentId;
    private String name;
    private String imageUrl;
    private String description;
    private Boolean isAvailable;
    private NourishmentTypeDocument nourishmentType;
    private String userId;
    private String categoryId;

}