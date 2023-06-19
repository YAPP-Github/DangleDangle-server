package yapp.be.apiapplication.system.exception

import java.time.LocalDateTime
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import yapp.be.exceptions.CustomException

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException::class)
    fun handleApiException(e: CustomException): ErrorResponse {
        return ErrorResponse(
            code = e.type.code,
            message = e.message,
            timeStamp = LocalDateTime.now()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class)
    fun handleApiException(e: RuntimeException): ErrorResponse {
        e.printStackTrace()
        return ErrorResponse(
            code = ApiExceptionType.RUNTIME_EXCEPTION.code,
            message = e.message ?: "",
            timeStamp = LocalDateTime.now()
        )
    }
}
