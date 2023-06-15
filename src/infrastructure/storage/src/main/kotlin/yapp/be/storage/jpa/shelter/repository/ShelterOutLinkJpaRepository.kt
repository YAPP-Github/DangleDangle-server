package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.shelter.model.ShelterOutlinkEntity

@Repository
interface ShelterOutLinkJpaRepository : JpaRepository<ShelterOutlinkEntity, Long>
