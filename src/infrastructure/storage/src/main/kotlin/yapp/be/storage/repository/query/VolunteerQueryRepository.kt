package yapp.be.storage.repository.query

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteer.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteer.repository.VolunteerJpaRepository
@Component
@Transactional(readOnly = true)
class VolunteerQueryRepository(
    private val jpaRepository: VolunteerJpaRepository
) : VolunteerQueryHandler {

    override fun findByEmail(email: String): Volunteer {
        val volunteerEntity = jpaRepository.findByEmail(email) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        return volunteerEntity.toDomainModel()
    }

    override fun findById(id: Long): Volunteer {
        val volunteerEntity = jpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return volunteerEntity.toDomainModel()
    }

    override fun isExistByEmail(email: String): Boolean {
        return jpaRepository.findByEmail(email) != null
    }

    override fun isExistByNickname(nickname: String): Boolean {
        return jpaRepository.findByNickname(nickname) != null
    }
}
