package yapp.be.storage.jpa.volunteer.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity

@Repository
interface VolunteerJpaRepository : JpaRepository<VolunteerEntity, Long> {
    fun findByEmail(email: String): VolunteerEntity?
    fun findByNickname(nickname: String): VolunteerEntity?
}
