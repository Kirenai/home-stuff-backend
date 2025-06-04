package me.kire.re.nourishment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Nourishment
 *
 * @author Kirenai RE
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Nourishment {

    private String nourishmentId;
    private String name;
    private String imageUrl;
    private String description;
    private Boolean isAvailable;
    private NourishmentType nourishmentType;
    private Long userId;
    private Long categoryId;

}