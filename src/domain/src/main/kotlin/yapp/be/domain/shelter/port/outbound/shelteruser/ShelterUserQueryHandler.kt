package yapp.be.domain.shelter.port.outbound.shelteruser

import yapp.be.domain.model.ShelterUser
import yapp.be.model.vo.Email

interface ShelterUserQueryHandler {

    fun findById(shelterUserId: Long): ShelterUser

    fun findByShelterId(shelterId: Long): ShelterUser
    fun findByEmail(email: Email): ShelterUser?
    fun existByEmail(email: Email): Boolean
}
