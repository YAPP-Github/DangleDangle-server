package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.VolunteerEventWaitingQueueJpaRepositoryCustom

interface VolunteerEventWaitingQueueJpaRepository : JpaRepository<VolunteerEventWaitingQueueEntity, Long>, VolunteerEventWaitingQueueJpaRepositoryCustom {
    fun findAllByVolunteerEventIdIn(volunteerEventIds: Collection<Long>): List<VolunteerEventWaitingQueueEntity>
}
