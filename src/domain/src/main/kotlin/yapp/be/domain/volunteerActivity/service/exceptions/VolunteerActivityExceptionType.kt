package yapp.be.domain.volunteerActivity.service.exceptions

import yapp.be.exceptions.CustomExceptionType

enum class VolunteerActivityExceptionType(override val code: String) : CustomExceptionType {
    ALREADY_PARTICIPATE("VOLUNTEER_EVENT-001"),
    PARTICIPATION_INFO_NOT_FOUND("VOLUNTEER_EVENT-002"),
    INVALID_RECRUIT_NUM_EDIT("VOLUNTEER_EVENT-003"),
    INVALID_DATE_RANGE_EDIT("VOLUNTEER_EVENT-004"),
    PARTICIPATION_VALIDATION_FAIL("VOLUNTEER_EVENT-005")
}
