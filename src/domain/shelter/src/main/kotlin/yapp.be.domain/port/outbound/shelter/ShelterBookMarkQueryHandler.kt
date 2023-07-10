package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.ShelterBookMark

interface ShelterBookMarkQueryHandler {
    fun getShelterIdAndVolunteerId(
        shelterId: Long,
        volunteerId: Long
    ): ShelterBookMark?
}
