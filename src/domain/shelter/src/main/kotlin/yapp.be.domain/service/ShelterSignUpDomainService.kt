package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.inbound.ShelterSignUpUseCase
import yapp.be.domain.port.outbound.ShelterUserCommandHandler
import yapp.be.model.Email

@Service
class ShelterSignUpDomainService(
    private val shelterUserCommandHandler: ShelterUserCommandHandler
) : ShelterSignUpUseCase {
    @Transactional
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
}
