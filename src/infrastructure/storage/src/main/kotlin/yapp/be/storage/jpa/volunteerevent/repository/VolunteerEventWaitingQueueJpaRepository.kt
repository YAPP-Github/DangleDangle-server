package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity

interface VolunteerEventWaitingQueueJpaRepository : JpaRepository<VolunteerEventWaitingQueueEntity, Long> {
    fun findAllByVolunteerEventIdIn(volunteerEventIds: Collection<Long>): List<VolunteerEventWaitingQueueEntity>
}
