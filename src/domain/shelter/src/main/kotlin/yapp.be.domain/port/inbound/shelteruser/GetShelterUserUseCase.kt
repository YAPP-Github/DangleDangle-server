package yapp.be.domain.port.inbound.shelteruser

import yapp.be.domain.model.ShelterUser
import yapp.be.model.vo.Email

interface GetShelterUserUseCase {
    fun getShelterUserById(shelterUserId: Long): ShelterUser
    fun getShelterUserByShelterId(shelterId: Long): ShelterUser
    fun getShelterUserByEmail(email: Email): ShelterUser?
    fun checkEmailExist(email: Email): Boolean
    fun checkNameExist(name: String): Boolean
}
