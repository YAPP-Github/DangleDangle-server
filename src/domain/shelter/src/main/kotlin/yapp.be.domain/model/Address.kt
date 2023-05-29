package yapp.be.domain.model

data class Address (
    private val city: String,
    private val borough: String,
    private val town: String,
    private val complexName: String,
    private val block: String,
    private val unit: String,
    private val roadName: String,
    private val mainBuildingNumber: Int,
    private val subBuildingNumber: Int,
    private val buildingName: String,
    private val lat: Double,
    private val lng: Double,
)
