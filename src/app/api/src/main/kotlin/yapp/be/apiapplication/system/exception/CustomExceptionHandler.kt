package yapp.be.apiapplication.system.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import java.time.LocalDateTime
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import yapp.be.exceptions.CustomException
import java.time.DateTimeException
import org.springframework.web.bind.MethodArgumentNotValidException
import yapp.be.exceptions.SystemExceptionType

private val logger = KotlinLogging.logger { }
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

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = "지원하지 않는 Http Method 입니다.",
            timeStamp = LocalDateTime.now()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMethodNotReadableException(exception: HttpMessageNotReadableException): ErrorResponse {
        exception.printStackTrace()
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = "잘못된 HttpBody 형식입니다.",
            timeStamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValidException(exception: MethodArgumentNotValidException): ErrorResponse {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class)
    fun handleApiException(e: RuntimeException, request: HttpServletRequest): ErrorResponse {
        logger.error { e.stackTraceToString() }
        println(MDC.get("requestHeader"))
        return ErrorResponse(
            exceptionCode = SystemExceptionType.RUNTIME_EXCEPTION.code,
            message = "Internal Server Error",
            timeStamp = LocalDateTime.now()
        )
    }
}
