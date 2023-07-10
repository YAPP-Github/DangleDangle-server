package yapp.be.domain.port.inbound.shelter

import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink

interface GetShelterUseCase {
    fun getShelterById(shelterId: Long): Shelter
    fun getShelterOutLinkByShelterId(shelterId: Long): List<ShelterOutLink>
}
