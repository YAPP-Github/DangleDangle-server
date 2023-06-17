package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.outbound.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.ObservationAnimalQueryHandler
import yapp.be.storage.jpa.observationanimal.model.mappers.toDomainModel
import yapp.be.storage.jpa.observationanimal.model.mappers.toEntityModel
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalJpaRepository

@Component
class ObservationAnimalRepository(
    private val observationAnimalJpaRepository: ObservationAnimalJpaRepository,
) : ObservationAnimalQueryHandler, ObservationAnimalCommandHandler {
    override fun create(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimal.toEntityModel()
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }
}
