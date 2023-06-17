package yapp.be.apiapplication.system.exception

import yapp.be.exceptions.CustomExceptionType

enum class ApiExceptionType(override val code: String) : CustomExceptionType {
    INTERNAL_SERVER_ERROR("API-000"),
}
