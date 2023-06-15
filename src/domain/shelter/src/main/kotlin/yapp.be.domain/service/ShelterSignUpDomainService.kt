package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.inbound.ShelterUserSignUpUseCase
import yapp.be.domain.port.outbound.ShelterUserCommandHandler
import yapp.be.model.Email

@Service
class ShelterSignUpDomainService(
    private val shelterUserCommandHandler: ShelterUserCommandHandler
) : ShelterUserSignUpUseCase {

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

    @Transactional
    override fun signUpWithAdditionalInfo(
        shelterId: Long,
        shelterUserId: Long,
        outLinks: List<ShelterOutLink>,
        parkingInfo: ShelterParkingInfo,
        donation: BankAccount,
        notice: String?
    ): ShelterUser {
        TODO("Not yet implemented")
    }
}
