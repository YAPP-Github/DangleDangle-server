package yapp.be.domain.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterBookMark
import yapp.be.domain.port.inbound.shelter.AddShelterBookMarkUseCase
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkQueryHandler

@Service
class AddShelterBookMarkDomainService(
    private val shelterBookMarkQueryHandler: ShelterBookMarkQueryHandler,
    private val shelterBookMarkCommandHandler: ShelterBookMarkCommandHandler
) : AddShelterBookMarkUseCase {
    @Transactional
    override fun doBookMark(shelterId: Long, volunteerId: Long): ShelterBookMark? {
        val shelterBookMark = shelterBookMarkQueryHandler.getShelterIdAndVolunteerId(
            shelterId = shelterId,
            volunteerId = volunteerId
        )

        return if (shelterBookMark == null) {
            val newShelterBookMark = ShelterBookMark(
                shelterId = shelterId,
                volunteerId = volunteerId
            )
            shelterBookMarkCommandHandler.saveBookMark(newShelterBookMark)
        } else {
            shelterBookMarkCommandHandler.deleteBookMark(
                shelterBookMark
            )
            null
        }
    }
}
