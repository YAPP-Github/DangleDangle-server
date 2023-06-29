package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterBookMark

interface ShelterBookMarkQueryHandler {
    fun getShelterIdAndVolunteerId(
        shelterId: Long,
        volunteerId: Long
    ): ShelterBookMark?
}
