package yapp.be.domain.port.inbound

import yapp.be.domain.model.ShelterBookMark

interface BookMarkShelterUseCase {
    fun doBookMark(
        shelterId: Long,
        volunteerId: Long
    ): ShelterBookMark?
}
