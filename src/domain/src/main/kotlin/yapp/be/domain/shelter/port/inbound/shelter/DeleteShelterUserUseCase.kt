package yapp.be.domain.port.inbound.shelter

interface DeleteShelterUserUseCase {
    fun deleteShelterUser(shelterUserId: Long): Long
}
