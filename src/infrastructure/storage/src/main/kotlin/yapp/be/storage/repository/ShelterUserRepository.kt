package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.outbound.ShelterUserCommandHandler
import yapp.be.domain.port.outbound.ShelterUserQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.model.vo.Email
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

    @Transactional(readOnly = true)
    override fun findById(shelterUserId: Long): ShelterUser {
        val shelterUserEntity = shelterUserJpaRepository.findByIdOrNull(shelterUserId) ?: throw CustomException(
            StorageExceptionType.ENTITY_NOT_FOUND, "보호소 사용자를 찾을 수 없습니다."
        )
        return shelterUserEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findByShelterId(shelterId: Long): ShelterUser {
        val shelterUserEntity = shelterUserJpaRepository.findByShelterId(shelterId)
            ?: throw CustomException(
                StorageExceptionType.ENTITY_NOT_FOUND, "보호소 사용자를 찾을 수 없습니다."
            )

        return shelterUserEntity.toDomainModel()
    }

    override fun findByEmail(email: Email): ShelterUser? {
        val shelterUserEntity = shelterUserJpaRepository.findByEmail(email.value)
        return shelterUserEntity?.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun existByEmail(email: Email): Boolean {
        return shelterUserJpaRepository.findByEmail(email.value) != null
    }
}
