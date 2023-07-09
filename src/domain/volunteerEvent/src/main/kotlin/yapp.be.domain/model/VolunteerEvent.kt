package yapp.be.domain.model

import yapp.be.enums.volunteerevent.VolunteerEventStatus
import yapp.be.enums.volunteerevent.AgeLimit
import yapp.be.enums.volunteerevent.VolunteerEventCategory
import java.time.LocalDate

data class VolunteerEvent(
    val id: Long,
    val shelterId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String,
    val ageLimit: AgeLimit,
    val date: LocalDate,
    val viewCnt: Int,
    val volunteerEventCategory: VolunteerEventCategory,
    val volunteerEventStatus: VolunteerEventStatus,
    val participantNum: Int,
)
