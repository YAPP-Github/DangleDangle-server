package yapp.be.storage.repository.command

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.outbound.VolunteerCommandHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteer.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteer.model.mappers.toEntityModel
import yapp.be.storage.jpa.volunteer.repository.VolunteerJpaRepository

@Component
@Transactional
class VolunteerCommandRepository(
    private val volunteerJpaRepository: VolunteerJpaRepository
) : VolunteerCommandHandler {

    override fun save(volunteer: Volunteer): Volunteer {
        val volunteerEntity = volunteerJpaRepository.save(
            volunteer.toEntityModel()
        )
        return volunteerEntity.toDomainModel()
    }

    override fun update(volunteer: Volunteer): Volunteer {
        val volunteerEntity = volunteerJpaRepository.findByIdOrNull(volunteer.id) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        volunteerEntity.update(volunteer)

        return volunteerJpaRepository.save(volunteerEntity).toDomainModel()
    }

    override fun hardDelete(volunteerId: Long) {
        return volunteerJpaRepository.deleteById(volunteerId)
    }

    override fun delete(volunteerId: Long): Volunteer {
        val volunteerEntity = volunteerJpaRepository.findByIdOrNull(volunteerId) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        volunteerJpaRepository.delete(volunteerEntity)
        return volunteerEntity.toDomainModel()
    }
}
