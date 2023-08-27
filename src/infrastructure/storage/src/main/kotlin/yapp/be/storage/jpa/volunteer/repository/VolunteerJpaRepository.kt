package yapp.be.storage.jpa.volunteer.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity

interface VolunteerJpaRepository : JpaRepository<VolunteerEntity, Long> {
    fun findByEmail(email: String): VolunteerEntity?
    fun findByNickname(nickname: String): VolunteerEntity?

    fun findAllByDeletedIsTrue(): List<VolunteerEntity>
}
