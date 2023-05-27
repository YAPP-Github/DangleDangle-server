package yapp.be.storage

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.VolunteerJpaRepository

@Component
class VolunteerRepository(
    private val jpaRepository: VolunteerJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
