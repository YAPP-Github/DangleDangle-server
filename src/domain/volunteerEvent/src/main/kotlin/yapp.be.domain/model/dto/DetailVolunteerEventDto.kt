package yapp.be.domain.model.dto

import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.vo.Address
import java.time.LocalDateTime

data class DetailVolunteerEventDto(
    val id: Long,
    val shelterName: String,
    val shelterProfileImageUrl: String?,
    val title: String,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val recruitNum: Int,
    val waitingVolunteers: List<VolunteerEventParticipantInfoDto>,
    val joiningVolunteers: List<VolunteerEventParticipantInfoDto>,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)

data class VolunteerEventParticipantInfoDto(
    val id: Long,
    val nickName: String
)
