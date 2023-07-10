package yapp.be.domain.service.observationanimal

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.inbound.observationanimal.DeleteObservationAnimalUseCase
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalCommandHandler

@Service
class DeleteObservationAnimalDomainService(
    private val observationAnimalCommandHandler: ObservationAnimalCommandHandler
) : DeleteObservationAnimalUseCase {
    @Transactional
    override fun deleteObservationAnimal(observationAnimalId: Long, shelterId: Long): ObservationAnimal {
        return observationAnimalCommandHandler.delete(
            observationAnimalId = observationAnimalId,
            shelterId = shelterId
        )
    }
}
