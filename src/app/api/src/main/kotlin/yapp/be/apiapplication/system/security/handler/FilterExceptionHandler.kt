package yapp.be.apiapplication.system.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.time.LocalDateTime
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ErrorResponse

@Component
class FilterExceptionHandler(
    private val objectMapper: ObjectMapper
) {
    @Throws(IOException::class)
    fun sendErrorMessage(res: HttpServletResponse, exceptionCode: String, message: String, httpStatus: Int) {
        res.status = httpStatus
        res.contentType = MediaType.APPLICATION_JSON.toString()
        res.characterEncoding = "UTF-8"
        res.writer.write(objectMapper.writeValueAsString(ErrorResponse(exceptionCode, message, LocalDateTime.now())))
    }
}
