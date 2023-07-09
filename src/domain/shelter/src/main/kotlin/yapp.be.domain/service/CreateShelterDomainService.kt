package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.model.Address
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.inbound.CreateShelterUseCase
import yapp.be.domain.port.outbound.ShelterCommandHandler

@Service
class CreateShelterDomainService(
    private val shelterCommandHandler: ShelterCommandHandler
) : CreateShelterUseCase {
    override fun create(
        name: String,
        description: String,
        phoneNumber: String,
        address: Address
    ): Shelter {
        val shelter = Shelter(
            name = name,
            description = description,
            phoneNumber = phoneNumber,
            address = address
        )
        return shelterCommandHandler.create(shelter)
    }
}
