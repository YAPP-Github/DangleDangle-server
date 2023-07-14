package yapp.be.domain.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.dto.ShelterDto
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler

@Service
class GetShelterDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val shelterOutLinkQueryHandler: ShelterOutLinkQueryHandler
) : GetShelterUseCase {
    @Transactional(readOnly = true)
    override fun getShelterById(shelterId: Long): Shelter {
        return shelterQueryHandler.findById(shelterId)
    }

    @Transactional(readOnly = true)
    override fun getNonMemberShelterInfoById(shelterId: Long): ShelterDto {
        return shelterQueryHandler.findInfoById(id = shelterId)
    }

    @Transactional(readOnly = true)
    override fun getMemberShelterInfoByIdAndVolunteerId(shelterId: Long, volunteerId: Long): ShelterDto {
        return shelterQueryHandler.findInfoByIdAndVolunteerId(
            id = shelterId,
            volunteerId = volunteerId
        )
    }

    @Transactional(readOnly = true)
    override fun getShelterOutLinkByShelterId(shelterId: Long): List<ShelterOutLink> {
        return shelterOutLinkQueryHandler.findAllByShelterId(shelterId)
    }
}
