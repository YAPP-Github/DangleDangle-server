package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.Shelter

interface ShelterCommandHandler {
    fun create(shelter: Shelter): Shelter
    fun update(shelter: Shelter): Shelter
    fun deleteById(shelterId: Long)
}
