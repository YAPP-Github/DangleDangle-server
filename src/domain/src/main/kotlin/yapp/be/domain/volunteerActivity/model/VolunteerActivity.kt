package yapp.be.domain.volunteerActivity.model

import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import java.time.LocalDateTime

data class VolunteerActivity(
    val id: Long = 0,
    val shelterId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val volunteerActivityCategory: VolunteerActivityCategory,
    val volunteerActivityStatus: VolunteerActivityStatus,
)
