package yapp.be.domain.model

import yapp.be.enums.volunteerevent.OutLinkType

data class ShelterOutLink(
    val id: Long = 0,
    val url: String,
    val type: OutLinkType,
    val shelterId: Long,
)
