package yapp.be.storage.jpa.event.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.model.enums.event.EventStatus
import yapp.be.storage.jpa.event.model.EventEntity

interface EventJpaRepository : JpaRepository<EventEntity, Long> {
    fun findByEventStatusOrderByCreatedAt(eventStatus: EventStatus = EventStatus.BEFORE_PROCESSING): List<EventEntity>
}
