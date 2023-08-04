package yapp.be.domain.port.outbound.shelter

import yapp.be.domain.model.Shelter
import yapp.be.domain.model.dto.ShelterDto

interface ShelterQueryHandler {
    fun findById(id: Long): Shelter
    fun findByAddressAndIsFavorite(
        address: String,
        volunteerId: Long,
    ): List<Shelter>
    fun findByAddress(
        address: String,
    ): List<Shelter>
    fun findInfoByIdAndVolunteerId(
        id: Long,
        volunteerId: Long
    ): ShelterDto

    fun findInfoById(
        id: Long
    ): ShelterDto
    fun existByName(name: String): Boolean
    fun findByLocation(latitude: Double, longitude: Double, size: Int): List<Shelter>
    fun findByLocationAndIsFavorite(latitude: Double, longitude: Double, size: Int, volunteerId: Long): List<Shelter>
}
