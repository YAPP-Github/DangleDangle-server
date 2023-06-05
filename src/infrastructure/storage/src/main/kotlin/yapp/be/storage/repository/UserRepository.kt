package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.user.repository.UserJpaRepository

@Component
class UserRepository(
    private val jpaRepository: UserJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
