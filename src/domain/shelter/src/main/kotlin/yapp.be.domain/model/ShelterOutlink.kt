package yapp.be.domain.model

import yapp.be.enum.OutlinkType

data class ShelterOutlink(
    val id: Long,
    val url: String,
    val type: OutlinkType,
    val shelterId: Long,
)
