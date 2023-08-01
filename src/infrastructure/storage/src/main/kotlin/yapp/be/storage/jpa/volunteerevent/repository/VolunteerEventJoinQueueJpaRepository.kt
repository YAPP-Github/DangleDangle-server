package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.VolunteerEventJoinQueueJpaRepositoryCustom

interface VolunteerEventJoinQueueJpaRepository : JpaRepository<VolunteerEventJoinQueueEntity, Long>, VolunteerEventJoinQueueJpaRepositoryCustom {
    fun findAllByVolunteerEventIdIn(volunteerEventIds: Collection<Long>): List<VolunteerEventJoinQueueEntity>
    fun findByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long): VolunteerEventJoinQueueEntity?

    fun countByVolunteerEventId(volunteerEventId: Long): Int
    fun deleteAllByVolunteerEventId(volunteerEventId: Long)
    fun deleteByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long)
}
