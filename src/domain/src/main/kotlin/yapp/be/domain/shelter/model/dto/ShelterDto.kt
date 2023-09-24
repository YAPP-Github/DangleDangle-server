package yapp.be.domain.model.dto

import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.model.vo.Address

data class ShelterDto(
    val id: Long = 0,
    val name: String,
    val description: String,
    val phoneNumber: String,
    val notice: String? = null,
    val profileImageUrl: String? = null,
    val bankAccount: BankAccount? = null,
    val address: Address,
    val parkingInfo: ShelterParkingInfo? = null,
    val bookMarked: Boolean
)
