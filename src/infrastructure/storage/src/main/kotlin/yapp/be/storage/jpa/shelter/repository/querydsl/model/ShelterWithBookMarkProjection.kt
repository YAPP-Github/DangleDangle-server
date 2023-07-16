package yapp.be.storage.jpa.shelter.repository.querydsl.model

import com.querydsl.core.annotations.QueryProjection
import yapp.be.storage.jpa.common.model.Address

class ShelterWithBookMarkProjection @QueryProjection constructor(
    val id: Long = 0,
    val name: String,
    val description: String,
    val address: Address,
    val phoneNum: String,
    val bankName: String?,
    val bankAccountNum: String?,
    val notice: String?,
    val parkingEnabled: Boolean?,
    val parkingNotice: String?,
    val profileImageUrl: String?,
    val bookMarked: Boolean,
)
