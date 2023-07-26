package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.GetVolunteerUseCase
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.model.vo.Email

@Service
class GetVolunteerDomainService(
    private val volunteerQueryHandler: VolunteerQueryHandler
) : GetVolunteerUseCase {

    @Transactional(readOnly = true)
    override fun getById(volunteerId: Long): Volunteer {
        return volunteerQueryHandler.findById(volunteerId)
    }

    @Transactional(readOnly = true)
    override fun getByEmail(email: Email): Volunteer {
        return volunteerQueryHandler.findByEmail(email.value)
    }
}
