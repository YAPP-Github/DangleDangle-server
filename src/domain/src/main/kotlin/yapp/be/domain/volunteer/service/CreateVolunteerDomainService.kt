package yapp.be.domain.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.inbound.CreateVolunteerUseCase
import yapp.be.domain.volunteer.port.inbound.model.CreateUserCommand
import yapp.be.domain.volunteer.port.outbound.VolunteerCommandHandler

@Service
class CreateVolunteerDomainService(
    private val volunteerCommandHandler: VolunteerCommandHandler
) : CreateVolunteerUseCase {
    @Transactional
    override fun create(command: CreateUserCommand): Volunteer {
        val volunteer = Volunteer(
            nickname = command.nickname,
            email = command.email,
            phone = command.phone,
            oAuthIdentifier = command.oAuthUserIdentifier
        )
        return volunteerCommandHandler.save(volunteer)
    }
}
