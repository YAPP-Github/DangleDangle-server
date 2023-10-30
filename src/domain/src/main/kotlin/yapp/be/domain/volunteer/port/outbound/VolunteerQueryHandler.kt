package yapp.be.domain.volunteer.port.outbound

import yapp.be.domain.volunteer.model.Volunteer

interface VolunteerQueryHandler {
    fun findByEmail(email: String): Volunteer
    fun findById(id: Long): Volunteer

    fun findAllByIds(ids: List<Long>): List<Volunteer>
    fun isExistByEmail(email: String): Boolean
    fun isExistByNickname(nickname: String): Boolean

    fun findAllByDeleteIsTrue(): List<Volunteer>
}
