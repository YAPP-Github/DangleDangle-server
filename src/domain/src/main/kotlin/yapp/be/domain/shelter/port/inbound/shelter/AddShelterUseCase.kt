package yapp.be.domain.port.inbound.shelter

import yapp.be.model.vo.Address
import yapp.be.domain.model.Shelter

interface AddShelterUseCase {
    fun create(
        name: String,
        description: String,
        phoneNumber: String,
        address: Address,
    ): Shelter
}
