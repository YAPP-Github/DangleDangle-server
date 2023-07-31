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
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    var description: String,
    @Column(nullable = false)
    @Embedded
    var address: Address,
    @Column(name = "phone_num", nullable = false)
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
    @Column(name = "is_alarm_enabled", nullable = false)
    var alarmEnabled: Boolean = true
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
        this.bankName = shelter.bankAccount?.name
        this.bankAccountNum = shelter.bankAccount?.accountNumber
        this.profileImageUrl = shelter.profileImageUrl
        this.parkingEnabled = shelter.parkingInfo?.parkingEnabled
        this.parkingNotice = shelter.parkingInfo?.parkingNotice
        this.notice = shelter.notice
        this.alarmEnabled = shelter.alarmEnabled
    }
}
