package yapp.be.domain.port.inbound

import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.domain.model.ShelterUser
import yapp.be.model.Email

interface ShelterUserSignUpUseCase {
    fun signUpWithEssentialInfo(
        shelterId: Long,
        email: Email,
        password: String,
        phoneNumber: String,
    ): ShelterUser
    fun signUpWithAdditionalInfo(
        shelterId: Long,
        shelterUserId: Long,
        outLinks: List<ShelterOutLink>,
        parkingInfo: ShelterParkingInfo,
        donation: BankAccount,
        notice: String?,
    ): ShelterUser
}
