package yapp.be.domain.model

import yapp.be.enum.AgeLimit
import yapp.be.enum.VolunteerEventStatus
import java.time.LocalDate

data class VolunteerEvent(
    val id: Long,
    val title: String,
    val recruitNum: Int,
    val material: String,
    val ageLimit: AgeLimit,
    val date: LocalDate,
    val viewCnt: Int,
    val volunteerEventStatus: VolunteerEventStatus,
    val participantNum: Int,
    val userId: Long,
    val shelterId: Long,
)
