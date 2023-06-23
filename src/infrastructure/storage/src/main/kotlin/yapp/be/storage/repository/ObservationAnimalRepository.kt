package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.outbound.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.ObservationAnimalQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.observationanimal.model.mappers.toDomainModel
import yapp.be.storage.jpa.observationanimal.model.mappers.toEntityModel
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalJpaRepository

@Component
class ObservationAnimalRepository(
    private val observationAnimalJpaRepository: ObservationAnimalJpaRepository,
) : ObservationAnimalQueryHandler, ObservationAnimalCommandHandler {

    @Transactional(readOnly = true)
    override fun findAllByShelterId(shelterId: Long): List<ObservationAnimal> {
        val observationAnimalEntities = observationAnimalJpaRepository.findAllByShelterId(shelterId)
        return observationAnimalEntities.map { it.toDomainModel() }
    }

    @Transactional(readOnly = true)
    override fun findById(observationAnimalId: Long): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdOrNull(observationAnimalId) ?: throw CustomException(
            StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found"
        )
        return observationAnimalEntity.toDomainModel()
    }

    @Transactional
    override fun create(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimal.toEntityModel()
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }

    @Transactional
    override fun update(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimal.toEntityModel()
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }

    @Transactional
    override fun delete(observationAnimalId: Long): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdOrNull(observationAnimalId) ?: throw CustomException(
            StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found"
        )
        observationAnimalJpaRepository.delete(observationAnimalEntity)

        return observationAnimalEntity.toDomainModel()
    }
}
