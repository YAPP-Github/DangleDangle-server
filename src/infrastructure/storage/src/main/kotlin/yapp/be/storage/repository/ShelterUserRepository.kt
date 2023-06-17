package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.outbound.ShelterUserCommandHandler
import yapp.be.domain.port.outbound.ShelterUserQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.model.Email
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterUserJpaRepository

@Component
class ShelterUserRepository(
    private val shelterUserJpaRepository: ShelterUserJpaRepository
) : ShelterUserQueryHandler, ShelterUserCommandHandler {
    @Transactional
    override fun save(shelterUser: ShelterUser): ShelterUser {
        val shelterUserEntity =
            shelterUserJpaRepository.save(
                shelterUser.toEntityModel()
            )
        return shelterUserEntity.toDomainModel()
    }

    override fun findById(shelterUserId: Long): ShelterUser {
        val shelterUserEntity = shelterUserJpaRepository.findByIdOrNull(shelterUserId) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter User Not Found")
        return shelterUserEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun existByEmail(email: Email): Boolean {
        return shelterUserJpaRepository.existsByEmail(email.value)
    }
}
