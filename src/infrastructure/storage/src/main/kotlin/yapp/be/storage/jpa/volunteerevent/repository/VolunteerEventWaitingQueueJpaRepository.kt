package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity

@Repository
interface VolunteerEventWaitingQueueJpaRepository : JpaRepository<VolunteerEventWaitingQueueEntity, Long> {
    fun findAllByVolunteerEventIdIn(volunteerEventIds: Collection<Long>): List<VolunteerEventWaitingQueueEntity>
}
