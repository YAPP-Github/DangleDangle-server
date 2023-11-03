package yapp.be.apiapplication.system.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import java.time.LocalDateTime
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import yapp.be.exceptions.SystemExceptionType

private val logger = KotlinLogging.logger { }

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
class UnKnownExceptionHandler {

    private val logger = KotlinLogging.logger { }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun handleApiException(e: Throwable, request: HttpServletRequest): ErrorResponse {
        logger.error { e.stackTraceToString() }

        return ErrorResponse(
            exceptionCode = SystemExceptionType.INTERNAL_SERVER_ERROR.code,
            message = "Internal Server Error",
            timeStamp = LocalDateTime.now()
        )
    }
}
