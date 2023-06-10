package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.*
import yapp.be.storage.jpa.common.model.Address

@Entity
@Table(name = "shelter")
class ShelterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", unique = true)
    val name: String,
    @Column(name = "description", columnDefinition = "TEXT")
    val description: String,
    @Column
    @Embedded
    val address: Address,
    @Column(name = "phone_num")
    val phone: String,
    @Column(name = "notice")
    val notice: String,
    @Column(name = "is_parking_enabled")
    val parkingEnabled: Boolean = false,
    @Column(name = "profile_image_url")
    val profileImageUrl: String,
    @Column(name = "email", unique = true)
    val email: String,
)
