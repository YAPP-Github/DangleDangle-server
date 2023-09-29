package yapp.be.domain.shelter.service.shelteruser.exceptions

import yapp.be.exceptions.CustomExceptionType

enum class ShelterUserExceptionType(override val code: String) : CustomExceptionType {
    ALREADY_PARTICIPATE("VOLUNTEER_EVENT-001"),
}
