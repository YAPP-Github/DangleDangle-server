package yapp.be.domain.port.inbound.shelter

import yapp.be.domain.model.ShelterBookMark

interface AddShelterBookMarkUseCase {
    fun doBookMark(
        shelterId: Long,
        volunteerId: Long
    ): ShelterBookMark?
}
