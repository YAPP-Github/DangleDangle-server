package yapp.be.domain.port.inbound

import yapp.be.domain.model.ShelterUser
import yapp.be.model.Email

interface GetShelterUserUseCase {
    fun getShelterUserById(shelterUserId: Long): ShelterUser
    fun checkEmailExist(email: Email): Boolean
}
