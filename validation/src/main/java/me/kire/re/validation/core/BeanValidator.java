package me.kire.re.validation.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.exceptions.dto.validator.ErrorResponse;
import me.kire.re.exceptions.model.validator.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeanValidator implements Validator {
    private final org.springframework.validation.Validator validator;

    @Override
    public <T> Mono<T> validate(T object) {
        log.info("Invoking BeanValidator#validate(..) method");
        Errors bindingResult = new BeanPropertyBindingResult(object, object.getClass().getName());
        this.validator.validate(object, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("Binding result has: {} errors", bindingResult.getFieldErrors().size());
            List<ErrorResponse> errorResponses = bindingResult.getFieldErrors()
                    .stream()
                    .map(ErrorResponse::new)
                    .toList();
            log.info("Error responses: {}", errorResponses);
            return Mono.error(new ValidatorException(errorResponses));
        }
        return Mono.just(object);
    }
}
