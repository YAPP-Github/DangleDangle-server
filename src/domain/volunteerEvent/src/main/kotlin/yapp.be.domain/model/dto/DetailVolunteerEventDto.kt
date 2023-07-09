package yapp.be.domain.model.dto

import yapp.be.enums.volunteerevent.AgeLimit
import yapp.be.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.enums.volunteerevent.VolunteerEventCategory
import yapp.be.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.Address
import java.time.LocalDateTime

data class DetailVolunteerEventDto(
    val title: String,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
