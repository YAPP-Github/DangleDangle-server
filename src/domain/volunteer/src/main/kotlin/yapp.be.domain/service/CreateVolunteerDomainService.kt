package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.CreateVolunteerUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.domain.port.outbound.VolunteerCommandHandler

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
        )
        return volunteerCommandHandler.save(volunteer)
    }
}
