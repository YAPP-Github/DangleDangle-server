package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.DeleteVolunteerUseCase
import yapp.be.domain.port.outbound.VolunteerCommandHandler

@Service
class DeleteVolunteerDomainService(
    private val volunteerCommandHandler: VolunteerCommandHandler
) : DeleteVolunteerUseCase {
    @Transactional
    override fun deleteVolunteer(volunteerId: Long): Volunteer {
        return volunteerCommandHandler.delete(volunteerId)
    }
}
