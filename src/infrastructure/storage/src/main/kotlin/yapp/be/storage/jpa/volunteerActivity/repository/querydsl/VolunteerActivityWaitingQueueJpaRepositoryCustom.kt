package yapp.be.storage.jpa.volunteerActivity.repository.querydsl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoProjection

interface VolunteerActivityWaitingQueueJpaRepositoryCustom {
    fun findAllWaitParticipantsByVolunteerActivityId(volunteerActivityId: Long): List<VolunteerEntity>

    fun findAllWaitingVolunteerActivityByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerActivityWithShelterInfoProjection>
}
