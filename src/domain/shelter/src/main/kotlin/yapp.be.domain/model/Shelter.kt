package yapp.be.domain.model

data class Shelter(
    val id: Long,
    val name: String,
    val description: String?,
    val phoneNumber: String,
    val notice: String?,
    val profileImageUrl: String?,
    val address: Address,
    val parkingInfo: ParkingNotification,
)
