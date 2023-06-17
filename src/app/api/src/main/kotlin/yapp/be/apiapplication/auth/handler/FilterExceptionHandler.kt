package yapp.be.apiapplication.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ErrorResponse
import java.io.IOException
import java.time.LocalDateTime

@Component
class FilterExceptionHandler(
    private val objectMapper: ObjectMapper,
) {
    @Throws(IOException::class)
    fun sendErrorMessage(res: HttpServletResponse, exceptionCode: String, message: String) {
        res.status = HttpServletResponse.SC_UNAUTHORIZED
        res.contentType = MediaType.APPLICATION_JSON.toString()
        res.characterEncoding = "UTF-8"
        res.writer.write(objectMapper.writeValueAsString(ErrorResponse(exceptionCode, message, LocalDateTime.now())))
    }
}
