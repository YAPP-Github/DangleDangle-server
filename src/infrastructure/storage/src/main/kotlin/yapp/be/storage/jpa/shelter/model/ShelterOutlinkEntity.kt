package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.*
import yapp.be.enum.OutlinkType

@Entity
@Table(name = "shelter_out_link")
class ShelterOutlinkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "url")
    val url: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "out_link_type")
    val outLinkType: OutlinkType,
    @Column(name = "shelter_id")
    val shelterId: Long,
)
