package yapp.be.storage.support.factory

import yapp.be.storage.jpa.common.model.Address
import yapp.be.storage.jpa.shelter.model.ShelterEntity

object ShelterEntityFactory {
    fun getShelterEntity(): ShelterEntity {
        return ShelterEntity(
            name = "테스트 보호소 명",
            description = "테스트 보호소 설명",
            address = Address(
                address = "도로명 주소",
                addressDetail = "세부 주소",
                postalCode = "우편번호",
                latitude = 0.0, // 위도
                longitude = 0.0 // 경도
            ),
            phoneNum = "핸드폰번호",
            bankName = "은행명",
            bankAccountNum = "은행계좌번호",
            notice = "공지",
            parkingEnabled = true, // 주차 가능 여부
            parkingNotice = "주차 공지사항",
            profileImageUrl = "보호소 대표사진"
        )
    }
}
