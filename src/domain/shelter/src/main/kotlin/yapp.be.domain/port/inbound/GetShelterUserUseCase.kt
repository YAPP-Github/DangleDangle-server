package yapp.be.domain.port.inbound

import yapp.be.domain.model.ShelterUser

interface GetShelterUserUseCase {
    fun getShelterUserById(shelterUserId: Long): ShelterUser
}
