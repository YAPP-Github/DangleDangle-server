package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventJoinQueueEntity

interface VolunteerEventJoinQueueJpaRepository : JpaRepository<VolunteerEventJoinQueueEntity, Long> {
    fun findAllByVolunteerEventIdIn(volunteerEventIds: Collection<Long>): List<VolunteerEventJoinQueueEntity>
}
