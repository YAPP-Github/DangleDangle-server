package yapp.be.domain.model


data class Shelter(
    val id: Long,
    val name: String,
    val description: String,
    val address: Address,
    val phone: String,
    val notice: String,
    val parking: Boolean = false,
    val account: String,
    val bankName: String,
    val donationUrl: String,
    val image: String,
    val email: String,
)
