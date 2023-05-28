package yapp.be.storage

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.VolunteerWaitingQueueJpaRepository

@Component
class VolunteerWaitingQueueRepository(
    private val jpaRepository: VolunteerWaitingQueueJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
