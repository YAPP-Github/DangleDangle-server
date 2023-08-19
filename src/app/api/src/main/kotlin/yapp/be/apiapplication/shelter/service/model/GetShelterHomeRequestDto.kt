package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDateTime

data class GetShelterHomeRequestDto(
    val shelterUserId: Long,
    val category: List<VolunteerEventCategory>?,
    val status: VolunteerEventStatus?,
    val from: LocalDateTime,
    val to: LocalDateTime,
)
