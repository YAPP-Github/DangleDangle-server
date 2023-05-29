package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerWaitingQueueQueryHandler
import yapp.be.storage.jpa.repository.VolunteerEventWaitingQueueJpaRepository

@Component
class VolunteerWaitingQueueRepository(
    private val jpaRepository: VolunteerEventWaitingQueueJpaRepository
) : VolunteerWaitingQueueQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
