package yapp.be.domain.port.inbound

import yapp.be.domain.model.Shelter

interface GetShelterUseCase {

    fun getShelterById(shelterId: Long): Shelter
}
