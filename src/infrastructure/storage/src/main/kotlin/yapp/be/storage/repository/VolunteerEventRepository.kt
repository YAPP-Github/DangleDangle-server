package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.storage.jpa.repository.VolunteerEventJpaRepository

@Component
class VolunteerEventRepository(
    private val jpaRepository: VolunteerEventJpaRepository
) : VolunteerEventQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
