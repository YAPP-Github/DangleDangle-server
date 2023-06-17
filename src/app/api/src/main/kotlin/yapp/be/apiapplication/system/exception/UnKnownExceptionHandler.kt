package yapp.be.apiapplication.system.exception

import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = LoggerFactory.getLogger(UnKnownExceptionHandler::class.java)

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
class UnKnownExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun handleApiException(ex: Throwable): ErrorResponse {
        logger.error(ex.message)
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INTERNAL_SERVER_ERROR.code,
            message = "Internal Server Error",
            timeStamp = LocalDateTime.now()
        )
    }
}
