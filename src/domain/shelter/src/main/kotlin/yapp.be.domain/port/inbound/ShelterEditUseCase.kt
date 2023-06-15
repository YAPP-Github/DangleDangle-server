package yapp.be.domain.port.inbound

import yapp.be.domain.model.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo

interface ShelterEditUseCase {
    fun editProfileImage(
        shelterId: Long,
        profileImageUrl: String
    ): Shelter
    fun editWithEssentialInfo(
        shelterId: Long,
        name: String,
        phoneNumber: String,
        description: String,
        address: Address
    ): Shelter
    fun editWithAdditionalInfo(
        shelterId: Long,
        parkingInfo: ShelterParkingInfo,
        bankAccount: BankAccount,
        notice: String?,
    ): Shelter

    fun editShelterOutLink(
        shelterId: Long,
        outLinks: List<ShelterOutLink>,
    ): List<ShelterOutLink>
}
