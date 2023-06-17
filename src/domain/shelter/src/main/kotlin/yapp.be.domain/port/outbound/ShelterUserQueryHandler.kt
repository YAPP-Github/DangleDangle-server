package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterUser
import yapp.be.model.Email

interface ShelterUserQueryHandler {

    fun findById(shelterUserId: Long): ShelterUser
    fun existByEmail(email: Email): Boolean
}
