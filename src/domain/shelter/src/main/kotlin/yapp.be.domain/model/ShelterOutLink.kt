package yapp.be.domain.model

import yapp.be.model.enums.volunteerevent.OutLinkType

data class ShelterOutLink(
    var id: Long = 0,
    val url: String,
    val type: OutLinkType,
    val shelterId: Long,
)
