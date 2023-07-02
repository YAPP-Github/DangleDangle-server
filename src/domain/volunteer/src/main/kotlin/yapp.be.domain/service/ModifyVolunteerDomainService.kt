package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.ModifyVolunteerUseCase
import yapp.be.domain.port.inbound.model.ModifyUserTokenCommand
import yapp.be.domain.port.outbound.VolunteerCommandHandler
import yapp.be.domain.port.outbound.VolunteerQueryHandler

@Service
class ModifyVolunteerDomainService(
    private val volunteerCommandHandler: VolunteerCommandHandler,
    private val volunteerQueryHandler: VolunteerQueryHandler,
) : ModifyVolunteerUseCase {
    @Transactional
    override fun updateUserToken(command: ModifyUserTokenCommand): Volunteer {
        val user = volunteerQueryHandler.findById(command.userId)
        val updatedVolunteer = Volunteer(
            id = command.userId,
            email = user.email,
            role = user.role,
            oAuthType = user.oAuthType,
            oAuthIdentifier = user.oAuthIdentifier,
            oAuthAccessToken = command.oAuth2AccessToken,
            oAuthRefreshToken = command.oAuth2RefreshToken,
            nickname = user.nickname,
            phone = user.phone,
        )
        return volunteerCommandHandler.saveToken(updatedVolunteer)
    }
}
