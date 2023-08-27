package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.VolunteerEventWaitingQueueJpaRepositoryCustom

interface VolunteerEventWaitingQueueJpaRepository : JpaRepository<VolunteerEventWaitingQueueEntity, Long>, VolunteerEventWaitingQueueJpaRepositoryCustom {

    fun findAllByVolunteerId(volunteerId: Long): List<VolunteerEventWaitingQueue>
    fun findByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long): VolunteerEventWaitingQueueEntity?
    fun findAllByVolunteerEventIdIn(volunteerEventIds: Collection<Long>): List<VolunteerEventWaitingQueueEntity>

    fun countByVolunteerEventId(volunteerEventId: Long): Int
    fun deleteAllByVolunteerEventId(volunteerId: Long)
    fun deleteByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long)
}
