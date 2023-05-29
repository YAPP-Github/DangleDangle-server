package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerJoinQueueQueryHandler
import yapp.be.storage.jpa.repository.VolunteerEventJoinQueueJpaRepository

@Component
class VolunteerJoinQueueRepository(
    private val jpaRepository: VolunteerEventJoinQueueJpaRepository
) : VolunteerJoinQueueQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
