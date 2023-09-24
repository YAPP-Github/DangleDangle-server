package yapp.be.domain.port.inbound.shelter

import yapp.be.model.vo.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo

interface EditShelterUseCase {

    fun editAlarmEnabled(
        shelterId: Long,
        alarmEnabled: Boolean
    ): Shelter
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
        parkingInfo: ShelterParkingInfo?,
        bankAccount: BankAccount?,
        notice: String?,
    ): Shelter

    fun editShelterOutLink(
        shelterId: Long,
        outLinks: List<ShelterOutLink>,
    ): List<ShelterOutLink>
}
