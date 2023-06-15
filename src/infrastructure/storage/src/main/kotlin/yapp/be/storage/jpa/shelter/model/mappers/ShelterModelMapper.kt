package yapp.be.storage.jpa.shelter.model.mappers

import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.storage.jpa.common.model.Address
import yapp.be.storage.jpa.shelter.model.ShelterEntity

fun Shelter.toEntityModel(): ShelterEntity {
    return ShelterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        address = Address(
            address = this.address.address,
            addressDetail = this.address.addressDetail,
            postalCode = this.address.postalCode,
            latitude = this.address.latitude,
            longitude = this.address.longitude
        ),
        phoneNum = this.phoneNumber,
        bankName = this.bankAccount?.name,
        bankAccountNum = this.bankAccount?.accountNumber,
        notice = this.notice,
        parkingEnabled = this.parkingInfo?.parkingEnabled,
        parkingNotice = this.parkingInfo?.notice,
        profileImageUrl = this.profileImageUrl
    )
}

fun ShelterEntity.toDomainModel(): Shelter {
    return Shelter(
        id = this.id,
        name = this.name,
        description = this.description,
        phoneNumber = this.phoneNum,
        notice = this.notice,
        profileImageUrl = this.profileImageUrl,
        bankAccount = run {
            if (this.bankName == null || this.bankAccountNum == null) null
            else BankAccount(name = this.bankName, accountNumber = this.bankAccountNum)
        },
        address = yapp.be.domain.model.Address(
            address = this.address.address,
            addressDetail = this.address.addressDetail,
            postalCode = this.address.postalCode,
            longitude = this.address.longitude,
            latitude = this.address.latitude
        ),
        parkingInfo = run {
            if (this.parkingEnabled == null || this.parkingNotice == null) null
            else ShelterParkingInfo(parkingEnabled = this.parkingEnabled, notice = this.parkingNotice)
        }
    )
}
