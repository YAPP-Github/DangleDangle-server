package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterBookMark

interface ShelterBookMarkCommandHandler {
    fun saveBookMark(shelterBookMark: ShelterBookMark): ShelterBookMark
    fun deleteBookMark(
        shelterBookMark: ShelterBookMark
    )
}
