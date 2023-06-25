package yapp.be.domain.port.outbound

import yapp.be.domain.model.Shelter

interface ShelterQueryHandler {
    fun findById(id: Long): Shelter
    fun existByName(name: String): Boolean
}
