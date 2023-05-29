package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerWaitingQueueQueryHandler
import yapp.be.storage.jpa.repository.VolunteerWaitingQueueJpaRepository

@Component
class VolunteerWaitingQueueRepository(
    private val jpaRepository: VolunteerWaitingQueueJpaRepository
) : VolunteerWaitingQueueQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
