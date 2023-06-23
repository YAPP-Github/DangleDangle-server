package yapp.be.domain.port.outbound

import yapp.be.domain.model.Volunteer

interface VolunteerQueryHandler {
    fun countAll(): Int
    fun findByEmail(email: String): Volunteer
    fun findById(id: Long): Volunteer
    fun isExistByEmail(email: String): Boolean
    fun isExistByNickname(nickname: String): Boolean
    fun save(volunteer: Volunteer): Volunteer
    fun saveToken(volunteer: Volunteer): Volunteer
}
