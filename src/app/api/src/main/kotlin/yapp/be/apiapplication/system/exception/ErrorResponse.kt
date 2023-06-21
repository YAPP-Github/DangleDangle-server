package yapp.be.apiapplication.system.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val exceptionCode: String,
    val message: String,
    val timeStamp: LocalDateTime
)
