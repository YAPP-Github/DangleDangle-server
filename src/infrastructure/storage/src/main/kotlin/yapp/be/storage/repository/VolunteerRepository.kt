package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.outbound.VolunteerCommandHandler
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteer.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteer.model.mappers.toEntityModel
import yapp.be.storage.jpa.volunteer.repository.VolunteerJpaRepository
@Component
class VolunteerRepository(
    private val jpaRepository: VolunteerJpaRepository
) : VolunteerQueryHandler, VolunteerCommandHandler {
    override fun save(volunteer: Volunteer): Volunteer {
        val volunteerEntity = jpaRepository.save(
            volunteer.toEntityModel()
        )
        return volunteerEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Volunteer {
        val volunteerEntity = jpaRepository.findByEmail(email) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        return volunteerEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Volunteer {
        val volunteerEntity = jpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return volunteerEntity.toDomainModel()
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
        val volunteerEntity = jpaRepository.findByIdOrNull(volunteer.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "User Not Found")
        volunteerEntity.update(volunteer)
        jpaRepository.save(volunteerEntity)

        return volunteerEntity.toDomainModel()
    }
}
