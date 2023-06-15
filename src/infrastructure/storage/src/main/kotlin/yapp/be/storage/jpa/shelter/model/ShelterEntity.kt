package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.*
import yapp.be.domain.model.Shelter
import yapp.be.storage.jpa.common.model.Address
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(name = "shelter")
class ShelterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", unique = true)
    var name: String,
    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,
    @Column
    @Embedded
    var address: Address,
    @Column(name = "phone_num")
    var phoneNum: String,
    @Column(name = "bank_name")
    var bankName: String?,
    @Column(name = "bank_account_num")
    var bankAccountNum: String?,
    @Column(name = "notice")
    var notice: String?,
    @Column(name = "is_parking_enabled")
    var parkingEnabled: Boolean?,
    @Column(name = "parking_notice")
    var parkingNotice: String?,
    @Column(name = "profile_image_url")
    var profileImageUrl: String?,
) : BaseTimeEntity() {

    fun update(shelter: Shelter) {
        this.name = shelter.name
        this.description = shelter.description
        this.phoneNum = shelter.phoneNumber
        this.address = Address(
            address = shelter.address.address,
            addressDetail = shelter.address.addressDetail,
            postalCode = shelter.address.postalCode,
            latitude = shelter.address.latitude,
            longitude = shelter.address.longitude
        )
        shelter.bankAccount?.let {
            this.bankName = it.name
            this.bankAccountNum = it.accountNumber
        }
        shelter.profileImageUrl?.let {
            this.profileImageUrl = it
        }
        shelter.parkingInfo?.let {
            this.parkingEnabled = it.parkingEnabled
            this.parkingNotice = it.notice
        }
        shelter.notice?.let { this.notice = it }
    }
}
