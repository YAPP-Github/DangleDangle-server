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
        val userEntity = volunteerQueryHandler.findById(command.userId)
        val updatedVolunteer = Volunteer(
            id = command.userId,
            email = userEntity.email,
            role = userEntity.role,
            oAuthType = userEntity.oAuthType,
            oAuthAccessToken = command.oAuth2AccessToken,
            oAuthRefreshToken = command.oAuth2RefreshToken,
            nickname = userEntity.nickname,
            phone = userEntity.phone,
        )
        return volunteerCommandHandler.saveToken(updatedVolunteer)
    }
}
