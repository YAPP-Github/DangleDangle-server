package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterUser

interface ShelterCommandHandler {
    fun save(shelterUser: ShelterUser): ShelterUser
}
