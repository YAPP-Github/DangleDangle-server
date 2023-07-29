package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterBookMark

interface ShelterBookMarkQueryHandler {

    fun getAllBookMarkedShelterByVolunteerId(
        volunteerId: Long
    ): List<Shelter>
    fun getShelterIdAndVolunteerId(
        shelterId: Long,
        volunteerId: Long
    ): ShelterBookMark?
}
