package yapp.be.domain.model

import yapp.be.enum.OutLinkType

data class ShelterOutlink(
    val id: Long,
    val url: String,
    val type: OutLinkType,
    val shelterId: Long,
)
