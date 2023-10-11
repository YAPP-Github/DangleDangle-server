package yapp.be.apiapplication.system.exception

import java.time.LocalDateTime
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import yapp.be.exceptions.CustomException
import java.time.DateTimeException
import org.springframework.web.bind.MethodArgumentNotValidException

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException::class)
    fun handleApiException(e: CustomException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = e.type.code,
            message = e.message,
            timeStamp = LocalDateTime.now()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeException::class)
    fun handleDateTimeException(e: DateTimeException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_DATE_EXCEPTION.code,
            message = e.message!!,
            timeStamp = LocalDateTime.now()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class)
    fun handleApiException(e: RuntimeException): ErrorResponse {
        e.printStackTrace()
        return ErrorResponse(
            exceptionCode = ApiExceptionType.RUNTIME_EXCEPTION.code,
            message = "Internal Server Error",
            timeStamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValid(exception: MethodArgumentNotValidException): ErrorResponse {
        val builder = StringBuilder()
        for (fieldError in exception.bindingResult.fieldErrors) {
            builder
                .append("[${fieldError.field}](은)는 ${fieldError.defaultMessage}")
                .append("입력된 값: ${fieldError.rejectedValue}")
                .append("|")
        }
        val message = builder.toString()
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = message.substring(0, message.lastIndexOf("|")),
            timeStamp = LocalDateTime.now()
        )
    }
}
