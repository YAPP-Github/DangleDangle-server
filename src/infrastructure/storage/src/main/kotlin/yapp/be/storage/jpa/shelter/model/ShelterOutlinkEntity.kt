package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.*
import yapp.be.enum.OutLinkType

@Entity
@Table(
    name = "shelter_out_link",
    indexes = [
        Index(name = "IDX_SHELTER_ID", columnList = "shelter_id")
    ]
)
class ShelterOutlinkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "url")
    val url: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "out_link_type")
    val outLinkType: OutLinkType,
    @Column(name = "shelter_id")
    val shelterId: Long,
)
