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
    val phoneNum: String,

    @Column(name = "bank_name")
    val bankName: String?,

    @Column(name = "bank_account_num")
    val bankAccountNum: String?,

    @Column(name = "notice")
    val notice: String?,
    @Column(name = "is_parking_enabled")
    val parkingEnabled: Boolean?,
    @Column(name = "parking_notice")
    val parkingNotice: String?,
    @Column(name = "profile_image_url")
    val profileImageUrl: String?,
)
