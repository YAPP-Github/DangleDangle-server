package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus

data class ParticipateVolunteerEventRequestDto(
    val shelterId: Long,
    val volunteerId: Long,
    val volunteerEventId: Long
)

data class ParticipateVolunteerEventResponseDto(
    val type: UserEventParticipationStatus,
    val volunteerEventId: Long
)
