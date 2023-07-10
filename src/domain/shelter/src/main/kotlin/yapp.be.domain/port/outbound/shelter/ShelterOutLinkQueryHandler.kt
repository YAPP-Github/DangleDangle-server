package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.ShelterOutLink

interface ShelterOutLinkQueryHandler {
    fun findAllByShelterId(shelterId: Long): List<ShelterOutLink>
}
