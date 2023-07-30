package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithShelterInfoProjection

interface VolunteerEventJoinQueueJpaRepositoryCustom {
    fun findAllJoinParticipantsByVolunteerEventId(volunteerEventId: Long): List<VolunteerEntity>

    fun findAllJoinVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerEventWithShelterInfoProjection>

    fun findAllDoneVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerEventWithShelterInfoProjection>
}
