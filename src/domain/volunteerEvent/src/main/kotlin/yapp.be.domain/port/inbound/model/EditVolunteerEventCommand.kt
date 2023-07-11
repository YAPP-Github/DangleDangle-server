package yapp.be.domain.port.inbound.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

data class EditVolunteerEventCommand(
    val volunteerEventId: Long,
    val shelterId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val category: VolunteerEventCategory,
    val status: VolunteerEventStatus,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
