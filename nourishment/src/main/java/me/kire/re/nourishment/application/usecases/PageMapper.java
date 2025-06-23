package me.kire.re.nourishment.application.usecases;

import me.kire.re.nourishment.domain.model.Nourishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageMapper {

    public Page<Nourishment> toPage(final List<Nourishment> nourishments,
                                    final Pageable pageable,
                                    final Long count) {
        return new PageImpl<>(nourishments, pageable, count);
    }
}
