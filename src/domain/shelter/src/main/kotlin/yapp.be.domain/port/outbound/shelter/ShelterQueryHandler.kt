package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.Shelter
import yapp.be.domain.model.dto.ShelterDto

interface ShelterQueryHandler {
    fun findById(id: Long): Shelter

    fun findInfoByIdAndVolunteerId(
        id: Long,
        volunteerId: Long
    ): ShelterDto

    fun findInfoById(
        id: Long
    ): ShelterDto
    fun existByName(name: String): Boolean
}
