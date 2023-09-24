package yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model

import com.querydsl.core.annotations.QueryProjection
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.storage.jpa.common.model.Address
import java.time.LocalDateTime

class VolunteerActivityWithShelterInfoProjection @QueryProjection constructor(
    val id: Long,
    val shelterId: Long,
    val shelterName: String,
    val shelterProfileImageUrl: String?,
    val title: String,
    val recruitNum: Int,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
