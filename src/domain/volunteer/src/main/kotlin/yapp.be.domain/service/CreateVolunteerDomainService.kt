package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.CreateVolunteerUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.domain.port.outbound.VolunteerQueryHandler

@Service
class CreateVolunteerDomainService(
    private val volunteerQueryHandler: VolunteerQueryHandler
) : CreateVolunteerUseCase {
    @Transactional
    override fun create(command: CreateUserCommand): Volunteer {
        val volunteer = Volunteer(
            nickname = command.nickname,
            email = command.email,
            phone = command.phone,
        )
        return volunteerQueryHandler.save(volunteer)
    }
}
