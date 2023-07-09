package yapp.be.domain.port.inbound

import yapp.be.model.Address
import yapp.be.domain.model.Shelter

interface CreateShelterUseCase {
    fun create(
        name: String,
        description: String,
        phoneNumber: String,
        address: Address,
    ): Shelter
}
