package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerJoinQueueQueryHandler
import yapp.be.storage.jpa.repository.VolunteerJoinQueueJpaRepository

@Component
class VolunteerJoinQueueRepository(
    private val jpaRepository: VolunteerJoinQueueJpaRepository
) : VolunteerJoinQueueQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
