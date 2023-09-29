package yapp.be.domain.volunteerActivity.model.dto

import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.vo.Address
import java.time.LocalDateTime

data class DetailVolunteerActivityDto(
    val id: Long,
    val shelterName: String,
    val shelterProfileImageUrl: String?,
    val title: String,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val recruitNum: Int,
    val waitingVolunteers: List<yapp.be.domain.volunteerActivity.model.dto.VolunteerActivityParticipantInfoDto>,
    val joiningVolunteers: List<yapp.be.domain.volunteerActivity.model.dto.VolunteerActivityParticipantInfoDto>,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)

data class VolunteerActivityParticipantInfoDto(
    val id: Long,
    val nickName: String
)
