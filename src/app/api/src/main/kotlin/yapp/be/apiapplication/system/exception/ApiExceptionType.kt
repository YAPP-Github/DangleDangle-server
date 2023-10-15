package yapp.be.apiapplication.system.exception

import yapp.be.exceptions.CustomExceptionType

enum class ApiExceptionType(override val code: String) : CustomExceptionType {
    UNAUTHORIZED_EXCEPTION("API-002"),
    UNAUTHENTICATED_EXCEPTION("API-003"),
    INVALID_DATE_EXCEPTION("API-004"),
    INVALID_PARAMETER("API-005"),
    INVALID_TOKEN("API-006"),
}
