package yapp.be.domain.service.shelter

import org.springframework.stereotype.Service
import yapp.be.model.vo.Address
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.inbound.shelter.AddShelterUseCase
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler

@Service
class AddShelterDomainService(
    private val shelterCommandHandler: ShelterCommandHandler
) : AddShelterUseCase {
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
