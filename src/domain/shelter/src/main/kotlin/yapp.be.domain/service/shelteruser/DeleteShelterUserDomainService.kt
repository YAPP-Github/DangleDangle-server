package yapp.be.domain.service.shelteruser

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.shelter.DeleteShelterUserUseCase
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkCommandHandler
import yapp.be.domain.port.outbound.shelteruser.ShelterUserCommandHandler

@Service
class DeleteShelterUserDomainService(
    private val shelterUserCommandHandler: ShelterUserCommandHandler,
    private val shelterCommandHandler: ShelterCommandHandler,
    private val observationAnimalCommandHandler: ObservationAnimalCommandHandler,
    private val shelterOutLinkCommandHandler: ShelterOutLinkCommandHandler,
    private val shelterBookMarkCommandHandler: ShelterBookMarkCommandHandler,
) : DeleteShelterUserUseCase {
    @Transactional
    override fun deleteShelterUser(shelterUserId: Long): Long {
        val shelterId = shelterUserCommandHandler.delete(shelterUserId)
        shelterCommandHandler.deleteById(shelterId)
        observationAnimalCommandHandler.deleteAllByShelterId(shelterId)
        shelterOutLinkCommandHandler.deleteAllByShelterId(shelterId)
        shelterBookMarkCommandHandler.deleteBookMarkByShelterId(shelterId)
        return shelterId
    }
}
