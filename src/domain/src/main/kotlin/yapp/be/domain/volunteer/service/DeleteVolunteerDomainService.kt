package yapp.be.domain.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.inbound.DeleteVolunteerUseCase
import yapp.be.domain.volunteer.port.outbound.VolunteerCommandHandler

@Service
class DeleteVolunteerDomainService(
    private val volunteerCommandHandler: VolunteerCommandHandler
) : DeleteVolunteerUseCase {
    @Transactional
    override fun deleteVolunteer(volunteerId: Long): Volunteer {
        return volunteerCommandHandler.delete(volunteerId)
    }

    @Transactional
    override fun hardDeleteVolunteer(volunteerId: Long) {
        volunteerCommandHandler.hardDelete(volunteerId)
    }
}
