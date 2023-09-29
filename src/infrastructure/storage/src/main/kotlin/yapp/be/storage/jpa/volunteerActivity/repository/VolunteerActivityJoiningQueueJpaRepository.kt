package yapp.be.storage.jpa.volunteerActivity.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityJoiningQueueEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.VolunteerActivityJoiningQueueJpaRepositoryCustom

interface VolunteerActivityJoiningQueueJpaRepository : JpaRepository<VolunteerActivityJoiningQueueEntity, Long>, VolunteerActivityJoiningQueueJpaRepositoryCustom {
    fun findAllByVolunteerActivityIdIn(volunteerActivityIds: Collection<Long>): List<VolunteerActivityJoiningQueueEntity>
    fun findByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerEventId: Long): VolunteerActivityJoiningQueueEntity?
    fun deleteAllByVolunteerActivityId(volunteerActivityId: Long)

    fun countByVolunteerActivityId(volunteerActivityId: Long): Int
    fun deleteByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerEventId: Long)
}
