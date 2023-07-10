package yapp.be.domain.service.shelter

import org.springframework.stereotype.Service
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler

@Service
class GetShelterDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val shelterOutLinkQueryHandler: ShelterOutLinkQueryHandler
) : GetShelterUseCase {
    override fun getShelterById(shelterId: Long): Shelter {
        return shelterQueryHandler.findById(shelterId)
    }

    override fun getShelterOutLinkByShelterId(shelterId: Long): List<ShelterOutLink> {
        return shelterOutLinkQueryHandler.findAllByShelterId(shelterId)
    }
}
