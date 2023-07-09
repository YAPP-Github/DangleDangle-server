package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import yapp.be.storage.jpa.volunteer.model.VolunteerEntity

interface VolunteerEventJoinQueueJpaRepositoryCustom {
    fun findAllJoinParticipantsByVolunteerEventId(volunteerEventId: Long): List<VolunteerEntity>
}
