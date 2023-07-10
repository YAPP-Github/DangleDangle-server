package yapp.be.domain.service.exceptions

import yapp.be.exceptions.CustomExceptionType

enum class VolunteerEventExceptionType(override val code: String) : CustomExceptionType {
    ALREADY_PARTICIPATE("VOLUNTEER_EVENT-001")
}
