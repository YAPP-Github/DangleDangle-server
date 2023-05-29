package yapp.be.domain.model

data class ShelterOutlink (
    val url: String,
    val type: Type,
    val shelterIdentifier: Identifier,
)
