package yapp.be.domain.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.dto.ShelterDto
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler

@Service
class GetShelterDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val shelterOutLinkQueryHandler: ShelterOutLinkQueryHandler,
    private val shelterBookMarkQueryHandler: ShelterBookMarkQueryHandler
) : GetShelterUseCase {
    @Transactional(readOnly = true)
    override fun getShelterById(shelterId: Long): Shelter {
        return shelterQueryHandler.findById(shelterId)
    }

    @Transactional(readOnly = true)
    override fun getVolunteerBookMarkedShelterByVolunteerId(volunteerId: Long): List<Shelter> {
        return shelterBookMarkQueryHandler
            .getAllBookMarkedShelterByVolunteerId(volunteerId)
    }

    @Transactional(readOnly = true)
    override fun getShelterByLocationAndIsFavorite(latitude: Double, longitude: Double, size: Int, volunteerId: Long?, isFavorite: Boolean?): List<Shelter> {
        return if (volunteerId != null) {
            shelterQueryHandler.findByLocationAndIsFavorite(latitude, longitude, size, volunteerId, isFavorite!!)
        } else {
            shelterQueryHandler.findByLocation(latitude, longitude, size)
        }
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
