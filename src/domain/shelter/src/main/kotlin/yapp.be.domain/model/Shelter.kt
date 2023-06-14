package yapp.be.domain.model

data class Shelter(
    val id: Long = 0,
    val name: String,
    val description: String,
    val phoneNumber: String,
    val notice: String? = null,
    val profileImageUrl: String? = null,
    val address: Address,
    val parkingInfo: ParkingNotification? = null,
)
