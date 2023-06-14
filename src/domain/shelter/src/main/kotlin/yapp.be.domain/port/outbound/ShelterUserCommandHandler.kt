package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterUser

interface ShelterUserCommandHandler {
    fun save(shelterUser: ShelterUser): ShelterUser
}
