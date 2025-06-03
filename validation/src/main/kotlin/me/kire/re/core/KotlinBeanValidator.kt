package me.kire.re.core
//
//import me.kire.re.dto.ErrorResponse
//import me.kire.re.exception.ValidatorException
//import org.slf4j.LoggerFactory
//import org.springframework.validation.BeanPropertyBindingResult
//import org.springframework.validation.Errors
//import org.springframework.validation.Validator
//import reactor.core.publisher.Mono
//
//@Component
//class KotlinBeanValidator(
//    private val validator: Validator
//) : me.kire.re.validation.core.Validator {
//    private val logger = LoggerFactory.getLogger(this.javaClass)
//
//    override fun <T : Any> validate(obj: T): Mono<T> {
//        logger.info("Invoking BeanValidator#validate(..) method")
//        val errors: Errors = BeanPropertyBindingResult(obj, obj::class.java.name)
//        this.validator.validate(obj, errors)
//
//        return if (errors.hasErrors()) {
//            logger.error("Binding result has: {} errors", errors.fieldErrors.size)
//            val errorResponse: List<ErrorResponse> = errors.fieldErrors.map { ErrorResponse(fieldError = it) }
//            logger.info("Error responses: {}", errorResponse)
//            Mono.error(ValidatorException(errorResponse))
//        } else Mono.just(obj)
//    }
//}