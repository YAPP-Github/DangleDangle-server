package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VolunteerEventActivityTypeMappingJpaRepository : JpaRepository<VolunteerEventActivityTypeMappingEntity, Long>
