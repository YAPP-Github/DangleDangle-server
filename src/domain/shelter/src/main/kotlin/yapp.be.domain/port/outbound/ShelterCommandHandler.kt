package yapp.be.domain.port.outbound

import yapp.be.domain.model.Shelter

interface ShelterCommandHandler {
    fun create(shelter: Shelter): Shelter
}
