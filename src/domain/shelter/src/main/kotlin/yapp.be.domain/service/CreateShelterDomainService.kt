package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.Address
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.inbound.CreateShelterUseCase

@Service
class CreateShelterDomainService : CreateShelterUseCase {
    override fun create(
        name: String,
        description: String?,
        phoneNumber: String,
        address: Address
    ): Shelter {
        TODO("Not yet implemented")
    }
}
