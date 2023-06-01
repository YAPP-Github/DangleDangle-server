package yapp.be.storage.jpa.model

import jakarta.persistence.*
import yapp.be.enum.OutlinkType

@Entity
@Table(name = "shelter_outlink")
class ShelterOutlink (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val url: String,
    @Column
    val outlinkType: OutlinkType,
    @Column
    val shelterId: String,
)
