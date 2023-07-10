package yapp.be.domain.model

import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import java.time.LocalDateTime

data class VolunteerEvent(
    val id: Long = 0,
    val shelterId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val volunteerEventCategory: VolunteerEventCategory,
    val volunteerEventStatus: VolunteerEventStatus,
)
