package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.GetVolunteerUseCase
import yapp.be.domain.port.outbound.VolunteerQueryHandler

@Service
class GetVolunteerDomainService(
    private val volunteerQueryHandler: VolunteerQueryHandler
) : GetVolunteerUseCase {
    @Transactional(readOnly = true)
    override fun getByEmail(email: String): Volunteer {
        return volunteerQueryHandler.findByEmail(email)
    }
}
