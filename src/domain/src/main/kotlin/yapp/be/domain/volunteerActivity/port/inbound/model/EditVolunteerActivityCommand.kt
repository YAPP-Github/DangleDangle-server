package yapp.be.domain.volunteerActivity.port.inbound.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

data class EditVolunteerActivityCommand(
    val volunteerEventId: Long,
    val shelterId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val category: VolunteerActivityCategory,
    val status: VolunteerActivityStatus,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
