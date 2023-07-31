package yapp.be.storage.jpa.volunteerevent.repository.querydsl.model

import com.querydsl.core.annotations.QueryProjection
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.common.model.Address
import java.time.LocalDateTime

class VolunteerEventWithShelterInfoProjection @QueryProjection constructor(
    val id: Long,
    val shelterName: String,
    val shelterProfileImageUrl: String?,
    val title: String,
    val recruitNum: Int,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
