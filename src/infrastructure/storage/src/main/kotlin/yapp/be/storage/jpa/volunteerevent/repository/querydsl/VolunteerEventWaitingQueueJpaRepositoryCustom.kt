package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import yapp.be.storage.jpa.volunteer.model.VolunteerEntity

interface VolunteerEventWaitingQueueJpaRepositoryCustom {
    fun findAllWaitParticipantsByVolunteerEventId(volunteerEventId: Long): List<VolunteerEntity>
}
