package yapp.be.domain.shelter.service.observationanimal

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.inbound.observationanimal.GetObservationAnimalUseCase
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalQueryHandler
import yapp.be.model.support.PagedResult

@Service
class GetObservationAnimalDomainService(
    private val observationAnimalQueryHandler: ObservationAnimalQueryHandler
) : GetObservationAnimalUseCase {

    @Transactional(readOnly = true)
    override fun getAllObservationAnimalsByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ObservationAnimal> {
        return observationAnimalQueryHandler.findAllByShelterId(
            shelterId = shelterId,
            page = page
        )
    }

    @Transactional(readOnly = true)
    override fun getObservationAnimalByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal {
        return observationAnimalQueryHandler.findByIdAndShelterId(
            observationAnimalId = observationAnimalId,
            shelterId = shelterId
        )
    }

    @Transactional(readOnly = true)
    override fun getObservationAnimalById(observationAnimalId: Long): ObservationAnimal {
        return observationAnimalQueryHandler.findById(observationAnimalId)
    }
}
