package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.ShelterBookMark

interface ShelterBookMarkCommandHandler {
    fun saveBookMark(shelterBookMark: ShelterBookMark): ShelterBookMark
    fun deleteBookMark(
        shelterBookMark: ShelterBookMark
    )
    fun deleteBookMarkByShelterId(shelterId: Long)
}
