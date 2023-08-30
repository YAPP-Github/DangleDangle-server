package yapp.be.storage.jpa.volunteerevent.repository.querydsl.model

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class ReminderVolunteerEventProjection @QueryProjection constructor(
    val volunteerEventId: Long,
    val shelterId: Long,
    val shelterName: String,
    val title: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)
