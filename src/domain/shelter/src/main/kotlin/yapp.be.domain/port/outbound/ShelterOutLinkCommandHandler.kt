package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterOutLink

interface ShelterOutLinkCommandHandler {
    fun upsertAll(outLinks: List<ShelterOutLink>): List<ShelterOutLink>
}
