package yapp.be.apiapplication.shelter.controller.model

import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import java.time.LocalDateTime

data class AddVolunteerEventRequest(
    val title: String,
    val description: String?,
    val category: VolunteerEventCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime

)
