package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ObservationAnimalQueryHandler
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalJpaRepository
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalTagJpaRepository
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalTagMappingJpaRepository

@Component
class ObservationAnimalRepository(
    private val observationAnimalJpaRepository: ObservationAnimalJpaRepository,
    private val observationAnimalTagMappingJpaRepository: ObservationAnimalTagMappingJpaRepository,
    private val observationAnimalTagJpaRepository: ObservationAnimalTagJpaRepository,
) : ObservationAnimalQueryHandler {
    override fun countAll(): Int {
        return observationAnimalJpaRepository.count().toInt()
    }
}
