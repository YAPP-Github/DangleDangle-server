package yapp.be.domain.service.shelteruser

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.inbound.shelteruser.SignUpShelterUseCase
import yapp.be.domain.port.outbound.shelteruser.ShelterUserCommandHandler
import yapp.be.model.vo.Email

@Service
class SignUpShelterDomainService(
    private val shelterUserCommandHandler: ShelterUserCommandHandler
) : SignUpShelterUseCase {
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
