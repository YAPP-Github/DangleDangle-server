package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.inbound.ShelterUserSignUpUseCase
import yapp.be.domain.port.outbound.ShelterUserCommandHandler
import yapp.be.model.Email

@Service
class ShelterSignUpDomainService(
    private val shelterUserCommandHandler: ShelterUserCommandHandler
) : ShelterUserSignUpUseCase {
    override fun signUpWithEssentialInfo(
        shelterId: Long,
        email: Email,
        password: String,
        phoneNumber: String
    ): ShelterUser {
        val shelterUser = ShelterUser(
            email = email,
            password = password,
            shelterId = shelterId
        )

        return shelterUserCommandHandler.save(shelterUser)
    }

    override fun signUpWithAdditionalInfo(): ShelterUser {
        TODO("Not yet implemented")
    }
}
