package yapp.be.apiapplication.volunteer.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
data class GetVolunteerVolunteerEventHistoryResponseDto(
    val shelterId: Long,
    val volunteerEventId: Long,
    val shelterName: String,
    val shelterImageProfileUrl: String?,
    val title: String,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val joiningNum: Int,
    val waitingNum: Int,
)
