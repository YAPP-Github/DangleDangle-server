package yapp.be.domain.model

import yapp.be.model.vo.Address

data class Shelter(
    val id: Long = 0,
    val name: String,
    val description: String,
    val phoneNumber: String,
    val notice: String? = null,
    val profileImageUrl: String? = null,
    val bankAccount: BankAccount? = null,
    val address: Address,
    val parkingInfo: ShelterParkingInfo? = null,
    val alarmEnabled: Boolean = true
)
