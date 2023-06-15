package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.outbound.ShelterCommandHandler
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository
import yapp.be.storage.jpa.shelter.repository.ShelterOutLinkJpaRepository

@Component
class ShelterRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterOutLinkJpaRepository: ShelterOutLinkJpaRepository,
) : ShelterQueryHandler, ShelterCommandHandler {

    @Transactional(readOnly = true)
    override fun findById(id: Long): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdOrNull(id) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        return shelterEntity.toDomainModel()
    }

    override fun create(shelter: Shelter): Shelter {
        val shelterEntity = shelterJpaRepository.save(
            shelter.toEntityModel()
        )

        return shelterEntity.toDomainModel()
    }

    override fun update(shelter: Shelter): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdOrNull(shelter.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        shelterEntity.update(shelter)
        shelterJpaRepository.save(shelterEntity)

        return shelterEntity.toDomainModel()
    }
}
