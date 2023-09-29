package yapp.be.storage.jpa.volunteerActivity.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityWaitingQueueEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.VolunteerActivityWaitingQueueJpaRepositoryCustom

interface VolunteerActivityWaitingQueueJpaRepository : JpaRepository<VolunteerActivityWaitingQueueEntity, Long>, VolunteerActivityWaitingQueueJpaRepositoryCustom {

    fun findAllByVolunteerId(volunteerId: Long): List<VolunteerActivityWaitingQueue>
    fun findByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerActivityId: Long): VolunteerActivityWaitingQueueEntity?
    fun findAllByVolunteerActivityIdIn(volunteerActivityIds: Collection<Long>): List<VolunteerActivityWaitingQueueEntity>
    fun deleteAllByVolunteerActivityId(volunteerActivityId: Long)
    fun countByVolunteerActivityId(volunteerEventId: Long): Int

    fun deleteByVolunteerId(volunteerId: Long)
    fun deleteByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerActivityId: Long)
}
