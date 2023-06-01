package yapp.be.domain.model

data class ShelterOutlink (
    val id: Long,
    val url: String,
    val type: Type,
    val shelterId: Long,
)
