package yapp.be.storage.jpa.volunteerevent.repository.querydsl.model

import com.querydsl.core.annotations.QueryProjection
import yapp.be.enums.volunteerevent.AgeLimit
import yapp.be.enums.volunteerevent.VolunteerEventCategory
import yapp.be.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.common.model.Address
import java.time.LocalDateTime

class VolunteerEventWithMyParticipationStatusProjection @QueryProjection constructor(
    val title: String,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val isJoining: Boolean,
    val isWaiting: Boolean,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
