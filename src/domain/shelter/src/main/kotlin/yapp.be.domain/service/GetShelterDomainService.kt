package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.inbound.GetShelterUseCase
import yapp.be.domain.port.outbound.ShelterQueryHandler

@Service
class GetShelterDomainService(
    private val shelterQueryHandler: ShelterQueryHandler
) : GetShelterUseCase {
    override fun getShelterById(shelterId: Long): Shelter {
        return shelterQueryHandler.findById(shelterId)
    }
}
