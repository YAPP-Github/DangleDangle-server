package yapp.be.domain.model

import java.time.LocalDate

data class VolunteerEvent (
    val title: String,
    val recruitNum: Int,
    val material: String,
    val ageLimit: AgeLimit,
    val date: LocalDate,
    val viewCnt: Int,
    val status: Status,
    val participantNum: Int,
    val userIdentifier: Identifier,
    val shelterIdentifier: Identifier,
)
