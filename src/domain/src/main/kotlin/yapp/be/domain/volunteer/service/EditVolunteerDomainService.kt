package yapp.be.domain.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.inbound.model.EditUserTokenCommand
import yapp.be.domain.volunteer.port.inbound.EditVolunteerUseCase
import yapp.be.domain.volunteer.port.inbound.model.EditVolunteerCommand
import yapp.be.domain.volunteer.port.outbound.VolunteerCommandHandler
import yapp.be.domain.volunteer.port.outbound.VolunteerQueryHandler

@Service
class EditVolunteerDomainService(
    private val volunteerCommandHandler: VolunteerCommandHandler,
    private val volunteerQueryHandler: VolunteerQueryHandler,
) : EditVolunteerUseCase {
    override fun updateVolunteer(command: EditVolunteerCommand): Volunteer {
        val volunteer = volunteerQueryHandler.findById(command.volunteerId)
        val updatedVolunteer = Volunteer(
            id = command.volunteerId,
            email = volunteer.email,
            role = volunteer.role,
            oAuthType = volunteer.oAuthType,
            oAuthIdentifier = volunteer.oAuthIdentifier,
            oAuthAccessToken = volunteer.oAuthAccessToken,
            oAuthRefreshToken = volunteer.oAuthRefreshToken,
            nickname = command.nickName,
            phone = command.phoneNum,
            alarmEnabled = command.alarmEnabled
        )

        return volunteerCommandHandler.update(updatedVolunteer)
    }

    @Transactional
    override fun updateUserToken(command: EditUserTokenCommand): Volunteer {
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
        return volunteerCommandHandler.update(updatedVolunteer)
    }
}
