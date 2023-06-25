package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteer.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteer.model.mappers.toEntityModel
import yapp.be.storage.jpa.volunteer.repository.VolunteerJpaRepository

@Repository
class VolunteerRepository(
    private val jpaRepository: VolunteerJpaRepository
) : VolunteerQueryHandler {
    @Transactional(readOnly = true)
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }

    override fun save(volunteer: Volunteer): Volunteer {
        val userEntity = jpaRepository.save(
            volunteer.toEntityModel()
        )
        return userEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Volunteer {
        val userEntity = jpaRepository.findByEmail(email) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        return userEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Volunteer {
        val userEntity = jpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return userEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun isExistByEmail(email: String): Boolean {
        return jpaRepository.findByEmail(email) != null
    }

    @Transactional(readOnly = true)
    override fun isExistByNickname(nickname: String): Boolean {
        return jpaRepository.findByNickname(nickname) != null
    }

    override fun saveToken(volunteer: Volunteer): Volunteer {
        val userEntity = jpaRepository.findByIdOrNull(volunteer.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "User Not Found")
        userEntity.update(volunteer)
        jpaRepository.save(userEntity)

        return userEntity.toDomainModel()
    }
}
